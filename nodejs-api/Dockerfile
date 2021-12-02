# Install dependencies only when needed
FROM node:17-slim AS deps
# Check https://github.com/nodejs/docker-node/tree/b4117f9333da4138b03a546ec926ef50a31506c3#node16-alpine to understand why libc6-compat might be needed.
#RUN apt install -y --no-cache libc6-compat
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install --frozen-lockfile

# Rebuild the source code only when needed
# FROM node:16-alpine AS builder
# WORKDIR /app
# COPY . .
# COPY --from=deps /app/node_modules ./node_modules
# RUN yarn build && yarn install --production --ignore-scripts --prefer-offline

# Production image, copy all the files and run next
FROM node:17-slim AS runner
WORKDIR /app

ENV NODE_ENV DEVELOPMENT

# RUN addgroup -g 1001 -S nodejs
# RUN adduser -S app -u 1001

COPY . .
COPY --from=deps /app/node_modules ./node_modules
COPY --from=deps /app/package.json ./package.json

USER root
RUN apt update && \
    apt install -y apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    lsb-release \
    wget \
    software-properties-common \
    dnsutils && \
    apt clean autoclean && \
    apt autoremove -y && \
    rm -rf /var/cache/apt/* && \
    rm -rf /var/lib/{apt,dpkg,cache,log}

# RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add
# RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 04EE7237B7D453EC
# RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 648ACFD622F3D138
RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian buster stable"
# RUN echo \
#    "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
#    $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

RUN apt update && \
    apt install -y docker-ce docker-ce-cli containerd.io && \
    apt clean autoclean && \
    apt autoremove -y && \
    rm -rf /var/cache/apt/* && \
    rm -rf /var/lib/{apt,dpkg,cache,log}

# USER app

EXPOSE 8080

CMD ["yarn", "start"]
