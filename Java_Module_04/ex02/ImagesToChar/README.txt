# Compiling and Running

- Open a terminal window in the project directory (`ImagesToChar/`).

```zsh
cd ./ImagesToChar
```

- Create `lib` directory:

```zsh
mkdir -p lib
```

- Download  `JAR` files in `lib` directory:

```zsh
curl -o lib/JColor-5.5.1.jar https://repo.maven.apache.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar
curl -o lib/jcommander-1.82.jar https://repo.maven.apache.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
```

- prepare `target` directory

```zsh
mkdir -p target
```

- extract jar files in the `target` directory

```zsh
jar xf lib/jcommander-1.82.jar
jar xf lib/JColor-5.5.1.jar
mv com target/com
```

- copy the recourses to the `target` directory:

```zsh
cp -R src/resources target
```

- Compile all the source code:  

```zsh
javac -cp target -d target ./src/java/fr/school42/printer/**/*.java
```

- Create the `JAR` file:

```zsh
jar cvfm target/images-to-chars-printer.jar src/manifest.txt -C target .
```

4. Run the application:

```zsh
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
```