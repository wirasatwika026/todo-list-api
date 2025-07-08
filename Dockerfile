# Multi-stage Dockerfile for Spring Boot Todo List API

# Stage 1: Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and configuration files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine AS runtime

# Install dumb-init for proper signal handling
RUN apk add --no-cache dumb-init

# Create non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy the jar file from builder stage
COPY --from=builder /app/target/todo-list-api-*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Set JVM options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

# Run the application with dumb-init for proper signal handling
ENTRYPOINT ["dumb-init", "--"]
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]