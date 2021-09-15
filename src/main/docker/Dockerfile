ARG GRAALVM_VERSION=21.2.0
FROM ghcr.io/graalvm/graalvm-ce:$GRAALVM_VERSION

RUN gu install native-image

ARG GRADLE_VERSION=7.2
RUN microdnf install unzip zip \
    && curl -s "https://get.sdkman.io" | bash \
    && chmod a+x "$HOME/.sdkman/bin/sdkman-init.sh" \
    && source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && sdk install gradle $GRADLE_VERSION

ARG UPX_VERSION=3.96
RUN microdnf install xz \
    && cd /tmp \
    && curl --fail --silent --location --retry 3 -O https://github.com/upx/upx/releases/download/v${UPX_VERSION}/upx-${UPX_VERSION}-amd64_linux.tar.xz \
    && tar -xvf upx-${UPX_VERSION}-amd64_linux.tar.xz \
    && cp upx-${UPX_VERSION}-amd64_linux/upx /usr/local/bin/ \
    && rm -rf upx-${UPX_VERSION}-amd64_linux*

ENTRYPOINT ["/bin/sh", "-c"]