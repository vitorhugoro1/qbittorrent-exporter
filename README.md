# qbittorrent-exporter

![Gradle Build](https://github.com/vitorhugoro1/qbittorrent-exporter/actions/workflows/build.yaml/badge.svg) ![Release](https://github.com/vitorhugoro1/qbittorrent-exporter/actions/workflows/release.yaml/badge.svg)

<img src="https://github.com/caseyscarborough/qbittorrent-grafana-dashboard/blob/master/images/logo.png" width=100> <img src="https://github.com/caseyscarborough/qbittorrent-grafana-dashboard/blob/master/images/prometheus.png" width=100>

This app is a Prometheus exporter for the qBittorrent application. It has been verified against qBittorrent 4.6.7 and 5.3.0.
Older versions (including 4.1.0 - 4.5.x) have not been tested with this fork and may not work.

This is especially useful when integrated with
the [qbittorrent-grafana-dashboard](https://github.com/caseyscarborough/qbittorrent-grafana-dashboard).

See it on [DockerHub](https://hub.docker.com/r/vitorhugoro1/qbittorrent-exporter).

## Usage

### docker

```bash
docker run \
    --name=qbittorrent-exporter \
    -e QBITTORRENT_USERNAME=username \
    -e QBITTORRENT_PASSWORD=password \
    -e QBITTORRENT_BASE_URL=http://localhost:8080 \
    -p 17871:17871 \
    vitorhugoro1/qbittorrent-exporter:latest
```

## Parameters

|         Parameter         |                               Function                               |      Default Value      |
|:-------------------------:|:--------------------------------------------------------------------:|:-----------------------:|
|        `-p 17871`         |                         The webservice port.                         |           N/A           |
| `-e QBITTORRENT_USERNAME` |                      The qBittorrent username.                       |         `admin`         |
| `-e QBITTORRENT_PASSWORD` |                      The qBittorrent password.                       |      `adminadmin`       |
| `-e QBITTORRENT_BASE_URL` |                      The qBittorrent base URL.                       | `http://localhost:8080` |
|   `-e QBITTORRENT_HOST`   |   The qBittorrent host. Ignored when using `QBITTORRENT_BASE_URL`.   |       `localhost`       |
|   `-e QBITTORRENT_PORT`   |   The qBittorrent port. Ignored when using `QBITTORRENT_BASE_URL`.   |         `8080`          |
| `-e QBITTORRENT_PROTOCOL` | The qBittorrent protocol. Ignored when using `QBITTORRENT_BASE_URL`. |         `http`          |

> **Note:** recent qBittorrent versions (4.6.1 and newer) no longer ship with the default `admin`/`adminadmin` credentials. Set your own WebUI
> username and password in qBittorrent and pass them to the exporter, otherwise login will fail.

## qBittorrent 5.x notes

- The session cookie is now named `QBT_SID_<port>` instead of `SID`; this is handled automatically.
- qBittorrent validates the `Host` header against the port it listens on. When running qBittorrent in Docker, make
  sure the published port matches the WebUI port inside the container (e.g. `-p 8080:8080`, not `-p 18080:8080`).
  Otherwise the exporter gets `401 Unauthorized`.
- Torrent states `pausedUP` and `pausedDL` were renamed to `stoppedUP` and `stoppedDL`. They appear in the `state`
  label of `qbittorrent_torrent_info` and the `name` label of `qbittorrent_torrent_states`, so update any Grafana
  panels or alerts that filter on `paused*`.

## Setup

Add the target to your `scrape_configs` in your `prometheus.yml` configuration file of your Prometheus server.

```yml
scrape_configs:

  - job_name: 'qbittorrent'
    static_configs:

      # Update your IP address and port here
      - targets: [ '192.168.1.100:17871' ]
```

## Building Locally

Build the app and the docker container using the following commands:

```bash
./gradlew installDist
docker build .
```

No local JDK? Build with Docker instead:

```bash
docker run --rm -v "$PWD":/app -w /app gradle:8-jdk17 gradle installDist
docker build .
```
