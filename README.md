# diff

Tool to find differences in file trees of two separate directories.

## About

The use-case this tool was created for is to create a model representing diff scans similar to the tools version control systems use.

The goal is to make this model serializable so the results of the diff can be easily worked with in other contexts such as reviews of detected changes.

## Functionality

Considering an "original" directory and a "revised" directory:

- Files that exist in the original directory, but not in the revised directory, are considered deleted files (`FILE_DELETED`)
- Files that exist in the revised directory, but not in the original directory, are considered added files (`FILE_ADDED`)
- Files existing in both directories that have changed are considered modified files (`FILE_MODIFIED`)
  - If such file is classified as source file, the contents of the original and revised versions are compared line-by-line

## Usage

Create a `DirectoryScan` by using the builder
```java
DirectoryScan scan = new DirectoryScanBuilder()
        .setOriginalDirectory(Paths.get("~/absolute/path/to/original"))
        .setRevisedDirectory(Paths.get("~/absolute/path/to/revised"))
        .setSourceFilePredicate(SourceFilePredicate.forFileExtensions("java", "kt", "xml", "fxml", "css"))
        .ignoreNonSourceFiles()
        .create();
```

Compute and retrieve the results
```java
Set<FileDiff> diff = scan.findDifferences();
```

If serializing the resulting set with Gson, the `DeltaSerializer` can be used for a cleaner outcome
```java
Gson gson = new GsonBuilder()
        .registerTypeAdapter(Delta.class, new DeltaSerializer())
        .create();
```
