# ------------------------------------------------------------------------------
#
# Example build and run.
#
# docker build -t ignite:latest .
#
# docker run -dit --net=streams-net --name=ignite -p 47100:47100 -p 47500:47500 -p 47501:47501 -p 47502:47502 -p 47503:47503 -p 47504:47504 -p 47505:47505 -p 47506:47506 -p 47507:47507 -p 47508:47508 -p 47509:47509 -p 11211:11211 -p 49112:49112 ignite:latest
# docker exec -it ignite bash
# docker logs --follow ignite
#
#
# ------------------------------------------------------------------------------

# Start from a Java image.
FROM centos:7

# Do not rely on anything provided by base image(s), but be explicit, if they are installed already it is noop then
RUN yum -y install mc telnet unzip java-11-openjdk java-11-openjdk-devel                   && \
    yum -y update                                                                          && \
    rm -Rf /var/cache/yum

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk/

COPY software/ignite.zip /home

RUN cd /home                                                  && \
    unzip -oq /home/ignite.zip                                && \
    rm -f /home/ignite.zip

COPY /scripts/server.xml /home/apache-ignite-2.7.6-bin/config/server.xml
COPY /scripts/stop-all-nodes.sh /home/apache-ignite-2.7.6-bin

#CMD /home/apache-ignite-2.7.6-bin/bin/ignite.sh /home/apache-ignite-2.7.6-bin/config/server.xml

EXPOSE 11211 47100 47500 47501 47502 47503 47504 47505 47506 47507 47508 47509 49112