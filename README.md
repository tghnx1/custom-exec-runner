<!-- Plugin description -->
Simple IntelliJ IDEA plugin that adds a custom Run/Debug Configuration
to execute arbitrary executables.

Features:
- Run predefined executables (rustc, cargo) from PATH
- Run any custom executable selected from local filesystem
- Pass command-line arguments to the executable
- Minimal UI following IntelliJ Platform guidelines

This plugin is a simple implementation created specifically for a test task.
<!-- Plugin description end -->
# Custom Exec Runner

Simple IntelliJ IDEA plugin that adds a Run/Debug Configuration
to run custom executables.

## Features
- Run Rust compiler (`rustc`) from PATH
- Run Cargo (`cargo`) from PATH
- Run any custom executable from local filesystem
- Pass command-line arguments
- Output is shown in the IDE Run Console

## Usage
1. Open **Run | Edit Configurations**
2. Add **Custom Exec Runner**
3. Choose executable:
  - rustc from PATH
  - cargo from PATH
  - or any custom executable
4. Specify arguments
5. Run or Debug

## Notes
This plugin provides a simple execution configuration.
It does not implement a custom debugger and relies on standard IDE process execution.
