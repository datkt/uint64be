uint64be
========

Encode and decode 64 bit integers into ByteArrays and Longs

## Installation

The `datkt.uint64be` package an be installed with NPM.

```sh
$ npm install @datkt/uint64be
```

## Prerequisites

* [Kotlin/Native](https://github.com/JetBrains/kotlin-native) and the
  `konanc` command line program.

## Usage

```sh
## Compile a program in 'main.kt' and link uint64be.klib found in `node_modules/`
$ konanc -r node_modules/@datkt -l uint64be/uint64be main.kt
```

where `main.kt` might be

```kotlin
import datkt.uint64be.*

fun main(args: Array<String>) {
  val enc = encode(0xdeadbeef)
  val dec = decode(env) // 0xdeadbeef == dec
}
```

## API

### `encode(int: Number): ByteArray`

Encodes an unsigned 64 bit integer number into a ByteArray.

### `decode(buffer: ByteArray, offset: Int = 0): Long`

Decodes an unsigned 64 bit integer from a ByteArray into a Long.

### `encodingLength(): Int`

Inline function to return constant length of 8 bytes

## See Also

* https://github.com/mafintosh/uint64be

## License

MIT
