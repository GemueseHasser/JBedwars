# basic-server based image
FROM gemuesehasser/basic-server

# define volume for the worlds
VOLUME ["/opt/server/world", "/opt/server/world_nether", "/opt/server/world_the_end"]

# copy deployment and server files
COPY --chown=nobody:nobody deployment_files .

# copy main plugin into server directory
COPY target/JBedwars.jar plugins/JBedwars.jar
