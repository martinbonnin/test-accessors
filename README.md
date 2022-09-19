To reproduce:

```
# Run the included build
./gradlew -p build-logic compileKotlin
# Run the main build, build-logic:compileKotlin will be run agin 
./gradlew help -i
[ ... ]
Task ':build-logic:compileKotlin' is not up-to-date because:
  Class path of task ':build-logic:compileKotlin' has changed from 5d5d066a31ec578a38e4c520e519ee10 to a0332dec08439c7ee6085c0ee519aec6.
 
```