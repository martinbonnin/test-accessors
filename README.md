To reproduce:

```
# Run the included build
./gradlew -p build-logic compileKotlin
# Run the root build, build-logic:compileKotlin will be run agin 
./gradlew help -i
[ ... ]
Task ':build-logic:compileKotlin' is not up-to-date because:
  Class path of task ':build-logic:compileKotlin' has changed from 5d5d066a31ec578a38e4c520e519ee10 to a0332dec08439c7ee6085c0ee519aec6.
 
```

Commenting out `enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")` in both projects makes the up-to-date check work again

Looking into the generated classes, looks like they differ only by the order of the constant pool strings, file names and timestamps:

```
$ javap -v /Users/mbonnin/git/test-accessors/build-logic/.gradle/7.4/dependencies-accessors/eaf6e422b407037b3630f94e47d7baced32e5a49/classes/org/gradle/accessors/dm/BuildLogicProjectDependency.class
Classfile /Users/mbonnin/git/test-accessors/build-logic/.gradle/7.4/dependencies-accessors/eaf6e422b407037b3630f94e47d7baced32e5a49/classes/org/gradle/accessors/dm/BuildLogicProjectDependency.class
  Last modified Sep 19, 2022; size 547 bytes
  SHA-256 checksum 0fb150be811df413d0de03fca024e5ea3817396f1e8be2b3ee3c55882d4ceb41
  Compiled from "BuildLogicProjectDependency.java"
public class org.gradle.accessors.dm.BuildLogicProjectDependency extends org.gradle.api.internal.catalog.DelegatingProjectDependency
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #7                          // org/gradle/accessors/dm/BuildLogicProjectDependency
  super_class: #2                         // org/gradle/api/internal/catalog/DelegatingProjectDependency
  interfaces: 0, fields: 0, methods: 1, attributes: 2
Constant pool:
   #1 = Methodref          #2.#3          // org/gradle/api/internal/catalog/DelegatingProjectDependency."<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
   #2 = Class              #4             // org/gradle/api/internal/catalog/DelegatingProjectDependency
   #3 = NameAndType        #5:#6          // "<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
   #4 = Utf8               org/gradle/api/internal/catalog/DelegatingProjectDependency
   #5 = Utf8               <init>
   #6 = Utf8               (Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
   #7 = Class              #8             // org/gradle/accessors/dm/BuildLogicProjectDependency
   #8 = Utf8               org/gradle/accessors/dm/BuildLogicProjectDependency
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               RuntimeVisibleAnnotations
  #12 = Utf8               Ljavax/inject/Inject;
  #13 = Utf8               SourceFile
  #14 = Utf8               BuildLogicProjectDependency.java
  #15 = Utf8               Lorg/gradle/api/NonNullApi;
{
  public org.gradle.accessors.dm.BuildLogicProjectDependency(org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory, org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal);
    descriptor: (Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=3
         0: aload_0
         1: aload_1
         2: aload_2
         3: invokespecial #1                  // Method org/gradle/api/internal/catalog/DelegatingProjectDependency."<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
         6: return
      LineNumberTable:
        line 17: 0
        line 18: 6
    RuntimeVisibleAnnotations:
      0: #12()
        javax.inject.Inject
}
SourceFile: "BuildLogicProjectDependency.java"
RuntimeVisibleAnnotations:
  0: #15()
    org.gradle.api.NonNullApi
```

```
$ javap -v /Users/mbonnin/git/test-accessors/.gradle/7.4/dependencies-accessors/eaf6e422b407037b3630f94e47d7baced32e5a49/classes/org/gradle/accessors/dm/RootProjectAccessor.class
Classfile /Users/mbonnin/git/test-accessors/.gradle/7.4/dependencies-accessors/eaf6e422b407037b3630f94e47d7baced32e5a49/classes/org/gradle/accessors/dm/RootProjectAccessor.class
  Last modified Sep 19, 2022; size 1098 bytes
  SHA-256 checksum 2fa3c1f2dfb9a54266148748cb94988efa1ccac6c9a389f026e90a195b751356
  Compiled from "RootProjectAccessor.java"
public class org.gradle.accessors.dm.RootProjectAccessor extends org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #10                         // org/gradle/accessors/dm/RootProjectAccessor
  super_class: #2                         // org/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory
  interfaces: 0, fields: 0, methods: 2, attributes: 2
Constant pool:
   #1 = Methodref          #2.#3          // org/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory."<init>":(Lorg/gradle/api/internal/artifacts/DefaultProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dsl/dependencies/ProjectFinder;)V
   #2 = Class              #4             // org/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory
   #3 = NameAndType        #5:#6          // "<init>":(Lorg/gradle/api/internal/artifacts/DefaultProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dsl/dependencies/ProjectFinder;)V
   #4 = Utf8               org/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory
   #5 = Utf8               <init>
   #6 = Utf8               (Lorg/gradle/api/internal/artifacts/DefaultProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dsl/dependencies/ProjectFinder;)V
   #7 = Class              #8             // org/gradle/accessors/dm/TestAccessorsProjectDependency
   #8 = Utf8               org/gradle/accessors/dm/TestAccessorsProjectDependency
   #9 = Methodref          #10.#11        // org/gradle/accessors/dm/RootProjectAccessor.getFactory:()Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;
  #10 = Class              #12            // org/gradle/accessors/dm/RootProjectAccessor
  #11 = NameAndType        #13:#14        // getFactory:()Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;
  #12 = Utf8               org/gradle/accessors/dm/RootProjectAccessor
  #13 = Utf8               getFactory
  #14 = Utf8               ()Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;
  #15 = String             #16            // :
  #16 = Utf8               :
  #17 = Methodref          #10.#18        // org/gradle/accessors/dm/RootProjectAccessor.create:(Ljava/lang/String;)Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;
  #18 = NameAndType        #19:#20        // create:(Ljava/lang/String;)Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;
  #19 = Utf8               create
  #20 = Utf8               (Ljava/lang/String;)Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;
  #21 = Methodref          #7.#22         // org/gradle/accessors/dm/TestAccessorsProjectDependency."<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
  #22 = NameAndType        #5:#23         // "<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
  #23 = Utf8               (Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
  #24 = Utf8               Code
  #25 = Utf8               LineNumberTable
  #26 = Utf8               RuntimeVisibleAnnotations
  #27 = Utf8               Ljavax/inject/Inject;
  #28 = Utf8               getTestAccessors
  #29 = Utf8               ()Lorg/gradle/accessors/dm/TestAccessorsProjectDependency;
  #30 = Utf8               SourceFile
  #31 = Utf8               RootProjectAccessor.java
  #32 = Utf8               Lorg/gradle/api/NonNullApi;
{
  public org.gradle.accessors.dm.RootProjectAccessor(org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory, org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder);
    descriptor: (Lorg/gradle/api/internal/artifacts/DefaultProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dsl/dependencies/ProjectFinder;)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=3
         0: aload_0
         1: aload_1
         2: aload_2
         3: invokespecial #1                  // Method org/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory."<init>":(Lorg/gradle/api/internal/artifacts/DefaultProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dsl/dependencies/ProjectFinder;)V
         6: return
      LineNumberTable:
        line 18: 0
        line 19: 6
    RuntimeVisibleAnnotations:
      0: #27()
        javax.inject.Inject

public org.gradle.accessors.dm.TestAccessorsProjectDependency getTestAccessors();
descriptor: ()Lorg/gradle/accessors/dm/TestAccessorsProjectDependency;
flags: (0x0001) ACC_PUBLIC
Code:
stack=5, locals=1, args_size=1
0: new           #7                  // class org/gradle/accessors/dm/TestAccessorsProjectDependency
3: dup
4: aload_0
5: invokevirtual #9                  // Method getFactory:()Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;
8: aload_0
9: ldc           #15                 // String :
11: invokevirtual #17                 // Method create:(Ljava/lang/String;)Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;
14: invokespecial #21                 // Method org/gradle/accessors/dm/TestAccessorsProjectDependency."<init>":(Lorg/gradle/api/internal/catalog/TypeSafeProjectDependencyFactory;Lorg/gradle/api/internal/artifacts/dependencies/ProjectDependencyInternal;)V
17: areturn
LineNumberTable:
line 24: 0
}
SourceFile: "RootProjectAccessor.java"
RuntimeVisibleAnnotations:
0: #32()
org.gradle.api.NonNullApi
```