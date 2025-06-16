# Gunakan base image Tomcat
FROM tomcat:9.0

# Install Ant untuk build project
RUN apt-get update && apt-get install -y ant

# Copy semua file ke dalam container
COPY . /app
WORKDIR /app

# Build menggunakan Ant
COPY lib/org-netbeans-modules-java-j2seproject-copylibstask.jar /app/lib/
RUN ant -Dlibs.CopyLibs.classpath=lib/org-netbeans-modules-java-j2seproject-copylibstask.jar

# Deploy hasil build ke Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY build/web /usr/local/tomcat/webapps/ROOT

# Buka port 8080
EXPOSE 8080

# Jalankan Tomcat
CMD ["catalina.sh", "run"]
