FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME       /usr/lib/jvm/java-8-oracle

RUN apt-get --purge remove openjdk*
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main" > /etc/apt/sources.list.d/webupd8team-java-trusty.list
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886
RUN apt-get update && \
  apt-get install -y --no-install-recommends oracle-java8-installer && \
  apt-get clean all
RUN wget --no-check-certificate https://github.com/getgauge/gauge/releases/download/v0.9.7/gauge-0.9.7-linux.x86_64.zip
RUN apt install unzip
RUN unzip gauge-0.9.7-linux.x86_64.zip -d /usr/local/bin
RUN wget --no-check-certificate https://github.com/getgauge/gauge-java/releases/download/v0.6.6/gauge-java-0.6.6-linux.x86_64.zip
RUN gauge install java --file gauge-java-0.6.6-linux.x86_64.zip
