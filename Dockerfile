FROM eclipse-temurin:17-jre
# Run ./gradlew installDist before running Docker build

COPY build/install/qbittorrent-exporter /opt/qbittorrent-exporter
ENTRYPOINT ["/opt/qbittorrent-exporter/bin/qbittorrent-exporter"]

EXPOSE 17871
