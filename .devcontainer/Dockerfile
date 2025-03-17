FROM debian:bookworm

ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

ARG INSTALL_GRADLE="true"
ARG GRADLE_VERSION=""

ENV SDKMAN_DIR="/usr/local/sdkman"

RUN useradd -ms /bin/bash vscode

## Install Corretto - the preferred JVM for Instaclustr
RUN apt-get update && apt-get install -y curl zip unzip which wget gpg git
RUN /bin/bash -c "wget -O - https://apt.corretto.aws/corretto.key | gpg --dearmor -o /usr/share/keyrings/corretto-keyring.gpg && \
echo \"deb [signed-by=/usr/share/keyrings/corretto-keyring.gpg] https://apt.corretto.aws stable main\" | tee /etc/apt/sources.list.d/corretto.list"
RUN apt-get update && apt-get install -y java-21-amazon-corretto-jdk

## Setup SDKMAN
RUN export SDKMAN_DIR="/usr/local/sdkman" && curl -s "https://get.sdkman.io" | bash
RUN chmod -R a+rwx "$SDKMAN_DIR/"
RUN /bin/bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh;"

##Install Gradle/Maven
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . $SDKMAN_DIR/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . $SDKMAN_DIR/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi
