# Contributing

Bug reports and focused pull requests are welcome. For shader compatibility reports, include the shader pack and version, Minecraft version, loader and Sodium versions, OS, GPU, and the relevant game log or shader compilation error. Do not attach private data or full logs without checking them first.

Before opening a pull request, run the build for both supported Minecraft versions:

```sh
./gradlew build
./gradlew build -Pminecraft=26.3
```

Use `gradlew.bat` on Windows. Keep changes focused and preserve the existing license and third-party attribution notices.
