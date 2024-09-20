# Compiling and Running

- Open a terminal window in the project directory (`ImagesToChar/`).

```zsh
cd ./ImagesToChar
```

- Compile all the source code:  

```zsh
javac -d target ./src/java/fr/school42/printer/**/*.java
```

- copy the recourses to the `target` directory:

```zsh
cp -R src/resources target
```

- Create the `JAR` file:

```zsh
jar cvfm target/images-to-chars-printer.jar src/manifest.txt -C target .
```

- Run the application:

```zsh
java -jar target/images-to-chars-printer.jar . 0
```
