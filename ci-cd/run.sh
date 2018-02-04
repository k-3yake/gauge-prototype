PWD=`pwd`
docker run -it -v "$PWD/..":"/gauge-prototype" gauge-prototype:latest  sh -c 'gauge run /gauge-prototype/specs/'
