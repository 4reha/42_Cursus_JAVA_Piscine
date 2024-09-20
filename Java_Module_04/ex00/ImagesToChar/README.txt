# Compiling and Running

- Open a terminal window in the project directory (`ImagesToChar/`).

```zsh
cd ./ImagesToChar
```

- Compile all the source code:  

```zsh
javac -d target ./src/java/fr/school42/printer/**/*.java
```

- Run the application:

```zsh
java -cp target fr.school42.printer.app.Main it.bmp . 0
```
