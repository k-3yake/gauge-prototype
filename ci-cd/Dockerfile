FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME       /usr/lib/jvm/java-8-oracle

# install gauge
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

# install maven
RUN apt-get install -y maven

# install gauge plugin
RUN wget --no-check-certificate https://github.com/getgauge/html-report/releases/download/v4.0.3/html-report-4.0.3-linux.x86_64.zip
RUN gauge install html-report --file html-report-4.0.3-linux.x86_64.zip

#文字化け対応
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
