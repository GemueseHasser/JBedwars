# java based image
FROM openjdk:17-jdk-alpine

# add the maintainer of the project
LABEL maintainer="Gemuese_Hasser"

# define environment variables
ENV VERSION='1.17.1'

# create server
RUN mkdir -p /opt/server \
    && cd /opt/server \
    && wget -O "paper.jar" "https://papermc.io/api/v1/paper/${VERSION}/latest/download" \
    && echo "eula = true" > /opt/server/eula.txt \
    && mkdir /plugins

# set up workdir
WORKDIR /opt/server

# define volume for the worlds
VOLUME ["/opt/server/world", "/opt/server/world_nether", "/opt/server/world_the_end"]

# copy deployment and server files
COPY deployment_files .

# copy main plugin into server directory
COPY target/JBedwars.jar plugins/JBedwars.jar
