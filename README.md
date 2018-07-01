# ID Obfuscator

ID Obfuscator is a Java library for obfuscating numerical identifiers. Practically, that means taking a number like "17" and transforming it into a string like "GDJSHCX" (and, presumably, turning that string back into a number later).

## The sales pitch

In the most common use case, you might have an application where you assign IDs to new users using a counter controlled by a central database. In this scheme, your first user would have an ID of 1, the second user would have an ID of 2, and so on. Ultimately, you might need to expose those identifiers to the public; for example, you might expose user profile pages with URLs like `https://example.com/user/1234`. This raises a few issues, though:

- It's easy for malicious users to find out exactly how many users you have
- It's easy for malicious users to crawl all your users' profile pages because the URLs are sequential
- A user's URL gives hints as to how long they've been a user, which may be undesirable in some cases

If we could use URLs that look more like `https://example.com/user/KJVTUYB`, those problems would go away. How can we get there, though? We could consider randomly-generating IDs, but then we'd have to deal with collisions (there's no guarantee we wouldn't randomly generate the same ID twice) and storing a mapping of random IDs to ordinal IDs. We could consider running the ordinal ID through a hash function, but ultimately this isn't too different from randomly generating IDs—it's still a one-way process and doesn't guarantee uniqueness.

What we'd really like is a system with some key properties:

- Obfuscated IDs are uniquely mapped to ordinal IDs
- No need to look up obfuscated IDs in a database
- Ordinal/obfuscated ID translation is fast

This is (surprise!) exactly what ID Obfuscator offers; it allows you to apply fast, reversible transformations to numerical IDs so it's easy for you to get the original ID, but hard for a malicious user to do the same. This allows you to obfuscate IDs without adding new infrastructure or worrying about ID collisions.

## Using ID Obfuscator

Let's begin with an example, then break it down:

```java
final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder()
        .includeUppercaseLatinLetters()
        .includeDigits()
        .excludeVowels()
        .excludeVisuallySimilarCharacters()
        .shuffleWithRandomSeed(0x4800211a78094023L)
        .build());

final MultiplicativeInverseIntegerTransformer inverse =
        new MultiplicativeInverseIntegerTransformer(0x5ee544ad);

final BitRotationIntegerTransformer rotate = new BitRotationIntegerTransformer(22);
final OffsetIntegerTransformer offset = new OffsetIntegerTransformer(0x3b2f0474);
final XorIntegerTransformer xor = new XorIntegerTransformer(0xd0593242);

final IntegerObfuscationPipeline pipeline =
        new IntegerObfuscationPipeline(codec, inverse, rotate, offset, xor);

System.out.println("| ID | Obfuscated ID |");
System.out.println("|----|---------------|");

for (int id = 0; id < 10; id++) {
    System.out.format("| %d  | %-13s |\n", id, pipeline.obfuscate(id));
    assert id == pipeline.deobfuscate(pipeline.obfuscate(id));
}
```

The example produces the following output:

| ID | Obfuscated ID |
|----|---------------|
| 0  | RZCNV8L       |
| 1  | 93ZWMWW       |
| 2  | Z93438Y       |
| 3  | FLMYVD7       |
| 4  | VQWMNT2       |
| 5  | CLM3NN9       |
| 6  | R44WR9B       |
| 7  | 9TB44NX       |
| 8  | ZKD3888       |
| 9  | BVJXQL8       |

In the above example, there are three major pieces of the puzzle: transformers, codecs, and a pipeline. We'll discuss each in turn.

### The pipeline

The main point of interaction with ID Obfuscator is the `ObfuscationPipeline`. The pipeline combines a number of transformers and exactly one codec into a coherent tool for obfuscating and deobfuscating numbers. The type, number, and configuration of the transformers and the type and configuration codec all control the behavior of the pipeline. As an example, let's change the order of the transformers in the demo above to `offset, rotate, xor, inverse` (i.e. we swap the positions of `offset` and `rotate`). Now the output looks like this:

| ID | Obfuscated ID |
|----|---------------|
| 0  | F8C88BC       |
| 1  | ZRHBHMQ       |
| 2  | C3VPHKD       |
| 3  | 43QZY42       |
| 4  | BDYTNP4       |
| 5  | RCLW873       |
| 6  | FQCYMLP       |
| 7  | ZDHXXV7       |
| 8  | CTZ7XZV       |
| 9  | 4TQCNTL       |

This is, obviously, very different from the original output. We could achieve similar output changes by changing the value of the offset passed to the `OffsetIntegerTransformer`, for example, or changing the random seed passed to the codec. This has two very important consequences:

1. A malicious user needs to know the exact type, order, and configuration of the transformers and codec in your pipeline in order to turn obfuscated IDs into their original numerical representations.
2. *You* need to know the exact type, order, and configuration of the transformers and codec in your pipeline in order to turn obfuscated IDs into their original numerical representations.

It's extremely important that you hold on to the "shape" and configuration of your pipeline once you start obfuscating IDs; if you lose it, you won't be able to deobfuscate your own IDs. Similarly, you *absolutely should not* randomly-generate pipeline parameters at runtime, because there's no guarantee they'll be the same from one run to the next. In other words, this is fine:

```java
offset = new OffsetIntegerTransformer(785374208);
```

…but this is an extremely bad idea:

```java
offset = new OffsetIntegerTransformer(new SecureRandom().nextInt());
```

### Transformers

Transformers reversibly transform one number into another number. As a trivial example, a transformer might transform an a number by adding 27 to it, and then later reverse the transformation by subtracting 27. As shown in the example above, ID Obfuscator provides a number of transformers out of the box, and each is configurable (so your `OffsetIntegerTransformer` may be very different from somebody else's).

Some of the transformers available out of the box include:

- `BitRotationIntegerTransformer` performs a [circular shift](https://en.wikipedia.org/wiki/Circular_shift) of configurable distance on the bits in a number
- `MultiplicativeInverseIntegerTransformer` obfuscates numbers by multiplying them by a "secret" you provide, then deobfuscates them by multiplying by the [multiplicative inverse](https://ericlippert.com/2013/11/12/math-from-scratch-part-thirteen-multiplicative-inverses/) of the secret
- `OffsetIntegerTransformer` obfuscates a number by adding a "secret" you provide, then deobfuscates by subtracting the secret
- `XorIntegerTransformer` obfuscates and deobfuscates numbers by applying a [bitwise XOR](https://en.wikipedia.org/wiki/Bitwise_operations_in_C#Bitwise_XOR_.22.5E.22) operation with a "secret" you provide

You're free to add your own transformers, too!

### Codecs

A codec takes a (possibly obfuscated) number and represents it as a string. Later, it can transform that string back into the original number. Codecs may provide a measure of obfuscation in their own right; for example, it might just represent the number as a decimal string, but shuffle the digits. You could, in principle, have a pipeline that has no transformers and only has a codec.

ID Obfuscator comes with `AlphabetCodec`, which uses an alphabet you provide to represent numbers as strings, but you can certainly provide your own codec, too.

## The details

ID Obfuscator is just that: an obfuscator. It makes it difficult for malicious users to figure out how to turn an obfuscated ID into a "real" ID, but not impossible. Under no circumstances should it be used to encode sensitive information like credit card numbers, PINs, or phone numbers. Caveat emptor.
