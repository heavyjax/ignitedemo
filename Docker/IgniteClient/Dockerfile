# ------------------------------------------------------------------------------
#
# Example build and run.
#
# docker build -t wf18:latest .
#
# docker run -dit --name streams_wf -v ~/tmp/app:/opt/jboss/wildfly/standalone/deployments/:rw -v ~/tmp/logs:/opt/jboss/wildfly/standalone/log/:rw --network=streams-net -p 9990:9990 -p 9999:9999 -p 8090:8080 wf18:latest
#
# tail -f /Users/heavyjax/tmp/logs/server.log
# docker logs --follow streams_wf
#
# docker exec -it streams_wf bash
#
# ------------------------------------------------------------------------------

FROM jboss/base-jdk:11

ENV WILDFLY_VERSION 18.0.1.Final
ENV WILDFLY_SHA1 ef0372589a0f08c36b15360fe7291721a7e3f7d9
ENV JBOSS_HOME /opt/jboss/wildfly
ENV JBOSS_HOME_BIN /opt/jboss/wildfly/bin

USER root

RUN yum -y install mc telnet bind-utils unzip java-11-openjdk java-11-openjdk-devel    && \
    yum -y update                                                                      && \
    rm -Rf /var/cache/yum

RUN cd $HOME && \
 	   curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
       sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 && \
       tar xf wildfly-$WILDFLY_VERSION.tar.gz && \
       mv wildfly-$WILDFLY_VERSION $JBOSS_HOME && \
       rm wildfly-$WILDFLY_VERSION.tar.gz

ENV LAUNCH_JBOSS_IN_BACKGROUND true

ADD ojdbc8.jar ${JBOSS_HOME}
ADD scripts/config-commands.cli /opt/jboss/wildfly/bin
ADD scripts/cli.properties /opt/jboss/wildfly/bin
ADD scripts/config.sh ${JBOSS_HOME}
ADD scripts/kafka.properties ${JBOSS_HOME}

# Change the ownership of added files/dirs to `jboss`
USER root

# Fix for Error: Could not rename /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current
RUN rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history


RUN chown jboss:0 /opt/jboss/wildfly/ojdbc8.jar                && \
    chmod +x /opt/jboss/wildfly/ojdbc8.jar                     && \
    chown jboss:0 /opt/jboss/wildfly/bin/config-commands.cli   && \
    chmod +x /opt/jboss/wildfly/bin/config-commands.cli        && \
    chown jboss:0 /opt/jboss/wildfly/bin/cli.properties        && \
    chmod +x /opt/jboss/wildfly/bin/cli.properties             && \
    chown jboss:0 /opt/jboss/wildfly/config.sh                 && \
    chmod +x /opt/jboss/wildfly/config.sh                      && \
    chown jboss:0 /opt/jboss/wildfly/kafka.properties          && \
    chmod +x /opt/jboss/wildfly/kafka.properties

# configure logging module
RUN mkdir -p  ${JBOSS_HOME}/modules/com/docker/wildfly/commons/main
ADD scripts/module.xml ${JBOSS_HOME}/modules/com/docker/wildfly/commons/main

# Run CLI wildfly configuration
RUN /opt/jboss/wildfly/config.sh                               && \
    chown -R jboss:0 ${JBOSS_HOME}                             && \
    chmod -R g+rw ${JBOSS_HOME}                                && \
    /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

USER jboss

EXPOSE 8090 9990 9999 8009 45700 7600 57600

EXPOSE 23364/udp 55200/udp 54200/udp 45688/udp

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
