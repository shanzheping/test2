FROM openjdk:7-jre

COPY pep-production.tar /usr/local/pep-production.tar

WORKDIR /usr/local

RUN tar -xf pep-production.tar
RUN rm -f pep-production.tar

EXPOSE 9090

ENTRYPOINT ["/usr/local/pep-production/run.sh"]
