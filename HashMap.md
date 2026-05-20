# HashMap: Detailed Teaching Content
## Complete Learning Material with Step-by-Step Explanations for Interviews

---

# PART 1: THE PROBLEM HASHMAP SOLVES

## 1.1 Before HashMap: What Was the Problem?

Let me start with a real-world scenario to help you understand WHY HashMap was created.

### Scenario: Building a Phone Book

Imagine you have a phone book with 1 million names and phone numbers. You want to:
- Store phone numbers
- Look up someone's number by name
- Add new entries
- Remove entries

```
Approach 1: Array of Names and Numbers

┌──────────────────────────────────────┐
│ Array:                               │
│ [0] → ("Alice", "9876543210")       │
│ [1] → ("Bob", "9876543211")         │
│ [2] → ("Charlie", "9876543212")     │
│ [3] → ("David", "9876543213")       │
│ ...                                  │
│ [999999] → ("Zoe", "9876543214")    │
└──────────────────────────────────────┘

Problem: To find "Charlie's" number:
1. Start at index 0: Is this "Charlie"? No
2. Check index 1: Is this "Charlie"? No
3. Check index 2: Is this "Charlie"? YES! Get number

You had to check 3 entries.
Worst case: Find last person → check 1,000,000 entries!
This is O(n) - LINEAR TIME!
```

**Why is O(n) bad?**

```
With 1 million names:
- Average lookup: 500,000 checks (takes seconds!)
- User experience: "App is slow, looking up one name takes forever"

With direct access (O(1)):
- Any lookup: 1 check (instant!)
- User experience: "Wow, instant lookup!"

This is the PROBLEM HashMap solves!
```

### Approach 2: Use the Name as an Index (Hash Table Concept)

```
Key idea: Instead of checking every entry,
          use the NAME to calculate which slot to look at!

Example:
Person: "Charlie"
↓
Use magical function: hash("Charlie") = 5
↓
Look at slot 5 directly!

┌──────────────────────────────────────┐
│ Array Slots (using hash as index):   │
│ [0] → null                           │
│ [1] → ("Bob", "9876543211")          │
│ [2] → null                           │
│ [3] → ("Alice", "9876543210")        │
│ [4] → null                           │
│ [5] → ("Charlie", "9876543212") ←─── Direct access!
│ [6] → null                           │
│ ...                                  │
└──────────────────────────────────────┘

To find "Charlie":
hash("Charlie") = 5
Look at position 5
FOUND! Instant!
```

**This is what HashMap does!**

## 1.2 The Core Insight: Hash Function

### What is a Hash Function?

Think of it like a **library card system**:

```
Library System:
Book Title: "The Great Gatsby"
↓
Librarian's special rule: 
  "Take the first letter and last letter,
   multiply by year written,
   mod by number of shelves"
↓
Shelf Number: 23

Next time you want "The Great Gatsby":
Apply same rule → Shelf 23
Go directly there!
```

**A hash function is similar:**

```
Input:  Any object (String, Integer, Custom Object)
↓
Magic transformation
↓
Output: Integer (the index/bucket number)
```

### Example: String Hash Function

```
String: "cat"

Step 1: Convert each character to ASCII value
c = 99
a = 97
t = 116

Step 2: Combine them (simplified version)
hash = (99 * 31^2) + (97 * 31^1) + (116 * 31^0)
     = (99 * 961) + (97 * 31) + 116
     = 95139 + 3007 + 116
     = 98262

Result: 98262 is the hash!

This is why "cat" will ALWAYS produce 98262
(deterministic - same input, same output)
```

### Why This Works: Requirements for Hash Function

```
REQUIREMENT 1: Deterministic
┌─────────────────────────────────────┐
│ hash("apple") MUST ALWAYS return    │
│ the same value!                     │
│                                     │
│ If you run the code 10 times,       │
│ "apple" must hash to same index     │
│ each time!                          │
└─────────────────────────────────────┘

Without this: Can't find anything you stored!


REQUIREMENT 2: Fast to Compute
┌─────────────────────────────────────┐
│ You need hash to be calculated      │
│ quickly (O(1) time)                 │
│                                     │
│ If hashing takes O(n) time,         │
│ then HashMap.get() also O(n)!       │
│                                     │
│ Defeats the purpose!                │
└─────────────────────────────────────┘


REQUIREMENT 3: Uniform Distribution
┌─────────────────────────────────────┐
│ Should spread values evenly         │
│                                     │
│ BAD distribution:                   │
│ hash("a") = 0                       │
│ hash("b") = 0                       │
│ hash("c") = 0                       │
│ All go to index 0! Clustered!       │
│                                     │
│ GOOD distribution:                  │
│ hash("a") = 0                       │
│ hash("b") = 5                       │
│ hash("c") = 12                      │
│ Spread across different indices!    │
└─────────────────────────────────────┘

With bad distribution:
HashMap becomes just a LinkedList
O(1) becomes O(n)!
```

## 1.3 From Hash to Array Index

### The Challenge: Hash Space vs Array Space

```
Problem:
========

Hash values: Can be ANY integer (-2 billion to +2 billion)
Array size:  Usually small (16, 32, 64, 128)

Example:
hash("apple") = 94,225,460
array size = 16

How do we fit 94,225,460 into array of size 16?

Answer: Modulo operation!
index = hash % array_size
index = 94,225,460 % 16 = 4

Now we can store at position 4!
```

### Modulo Operation Explained

```
94,225,460 % 16 = ?

Think of it as: "What's the remainder when dividing by 16?"

94,225,460 ÷ 16 = 5,889,091 with remainder 4
                                        ↑
                                    THIS is the answer!

Why?
94,225,460 = (16 × 5,889,091) + 4
                                └─ remainder (our index)

So: 94,225,460 % 16 = 4

Another example:
100 % 16:
100 ÷ 16 = 6 remainder 4
100 % 16 = 4

99 % 16 = 3
98 % 16 = 2
97 % 16 = 1
96 % 16 = 0
95 % 16 = 15
94 % 16 = 14

See the pattern? It always produces 0-15 for % 16!
This ensures index is always within array bounds.
```

### Why Modulo is Slow (and the Optimization)

```
Modulo Operation:
index = hash % 16

CPU cycles needed: ~20-30 cycles (slow!)

Faster approach using BITWISE AND:
index = hash & 15

CPU cycles needed: 1 cycle (fast!)

BUT: This ONLY works if size is power of 2!

Why?
Powers of 2 in binary:
16 = 0b0010000
16 - 1 = 15 = 0b0001111  ← All 1s!

Bitwise AND with all 1s gives you last 4 bits:
hash & 0b0001111 = last 4 bits of hash

Bits can be: 0000 to 1111 = 0 to 15 ✓

Non-power-of-2:
17 = 0b0010001
17 - 1 = 16 = 0b0010000  ← NOT all 1s

hash & 0b0010000 = doesn't work correctly!
Doesn't distribute evenly!

THIS IS WHY HashMap capacity is always power of 2:
16, 32, 64, 128, 256, ...
NOT: 17, 25, 37, ...
```

---

# PART 2: COLLISION - THE REAL PROBLEM

## 2.1 What is a Collision?

### Definition with Example

```
Collision = Two DIFFERENT keys produce SAME hash value

Example:
"apple" → hash = 94,225,460 → index = 94,225,460 % 16 = 4
"grape" → hash = 94,225,472 → index = 94,225,472 % 16 = 4

Both want to go to index 4!

This is a COLLISION!
```

### Why Do Collisions Happen? (Pigeonhole Principle)

```
The Pigeonhole Principle:
"If you have N pigeons and M pigeonholes where N > M,
 then at least one pigeonhole must contain multiple pigeons"

Applied to HashMap:
Pigeons: All possible strings (infinite!)
Pigeonholes: Array slots (16, 32, 64, ...)

Since infinite strings > finite slots:
COLLISIONS ARE INEVITABLE!

Even with good hash function!

Example with small numbers:
Array size: 5
Hashing just numbers 1-10

hash(1) % 5 = 1
hash(2) % 5 = 2
hash(3) % 5 = 3
hash(4) % 5 = 4
hash(5) % 5 = 0
hash(6) % 5 = 1  ← COLLISION with hash(1)!
hash(7) % 5 = 2  ← COLLISION with hash(2)!
...

With 10 numbers and 5 slots:
Mathematically guaranteed collisions!
```

### Visual Example of Collisions

```
Without collisions (dream scenario):
Array size: 5
Entries: 5

Index 0: Entry("zero")
Index 1: Entry("one")
Index 2: Entry("two")
Index 3: Entry("three")
Index 4: Entry("four")

Perfect! One per slot.
GET time: O(1) - instant!

With collisions (reality):
Array size: 5
Entries: 7

Index 0: Entry("zero")
Index 1: Entry("one") → Entry("six") → null     ← 2 entries!
Index 2: Entry("two") → Entry("seven") → null   ← 2 entries!
Index 3: Entry("three")
Index 4: Entry("four") → Entry("five") → null   ← 3 entries!

Multiple entries at one index!
Index 4 has 3 entries - need to check each!
GET time: O(3) instead of O(1)
```

---

# PART 3: SOLUTION - CHAINING

## 3.1 What is Chaining?

### Core Idea

```
Problem: Two keys hash to same index, where to store both?

Solution: Use a LINKED LIST at each array slot!

Instead of:
Index → Single Entry

Use:
Index → Head of Linked List → Next Entry → ... → null
```

### Linked List Basics (Quick Review)

If you don't know LinkedLists well, here's a quick refresh:

```
A LinkedList node:
┌─────────────────────────┐
│  Node                   │
├─────────────────────────┤
│  data: value            │
│  next: pointer to next  │
└─────────────────────────┘

Connecting nodes:
┌────────────┐         ┌────────────┐         ┌────────────┐
│  Node 1    │         │  Node 2    │         │  Node 3    │
│  data: 10  │         │  data: 20  │         │  data: 30  │
│  next: ●───┼────────→│  next: ●───┼────────→│  next: null│
└────────────┘         └────────────┘         └────────────┘
     ↑
   HEAD

To traverse:
current = HEAD
while (current != null) {
    process(current.data)
    current = current.next
}
```

### HashMap Uses LinkedList for Collisions

```
HashMap's Node structure:

┌──────────────────────────────────────┐
│  Node<K, V>                          │
├──────────────────────────────────────┤
│  hash: int                ← Cached!  │
│  key: K                              │
│  value: V                            │
│  next: Node<K,V>  ← Points to next   │
└──────────────────────────────────────┘

In array:
Index 0: null
Index 1: Node("key1", "val1") → Node("key2", "val2") → null
Index 2: null
Index 3: Node("key3", "val3") → null
Index 4: null

At each index, we have a CHAIN of nodes!
```

## 3.2 Handling Collisions Step-by-Step

### Scenario: Adding Entries with Collision

```
Initial state:
Array size: 16
Entry 0: All slots empty

hash table:
[0]: null
[1]: null
...
[15]: null
```

### Step 1: PUT First Entry

```
map.put("apple", 10)

Step 1: Calculate hash
hash("apple") = 94,225,460

Step 2: Calculate index
index = 94,225,460 % 16 = 4

Step 3: Check if slot 4 is empty
table[4] == null? YES

Step 4: Create node
node = Node(
    hash=94,225,460,
    key="apple",
    value=10,
    next=null
)

Step 5: Store at index 4
table[4] = node

Result:
[0]: null
[1]: null
[2]: null
[3]: null
[4]: Node("apple", 10) → null   ← New entry
[5]: null
...

Time: O(1) - direct insertion!
```

### Step 2: PUT Entry with DIFFERENT Hash (No Collision)

```
map.put("banana", 20)

Step 1: Calculate hash
hash("banana") = 94,225,472

Step 2: Calculate index
index = 94,225,472 % 16 = 8  ← Different index!

Step 3: Check if slot 8 is empty
table[8] == null? YES

Step 4: Create node
node = Node(hash=94,225,472, key="banana", value=20, next=null)

Step 5: Store at index 8
table[8] = node

Result:
[0]: null
[1]: null
[2]: null
[3]: null
[4]: Node("apple", 10) → null
[5]: null
[6]: null
[7]: null
[8]: Node("banana", 20) → null   ← New entry at different index
[9]: null
...

No collision! Both entries at different indices!
```

### Step 3: PUT Entry with SAME Hash (COLLISION!)

```
map.put("apricot", 30)

Step 1: Calculate hash
hash("apricot") = 94,225,490

Step 2: Calculate index
index = 94,225,490 % 16 = 4  ← SAME as "apple"!

Step 3: Check if slot 4 is empty
table[4] == null? NO! Already has "apple"

COLLISION DETECTED!

Step 4: What to do?
Option: Add to chain!

Current state at index 4:
table[4] → Node("apple", 10) → null

Step 5: Create new node
newNode = Node(hash=94,225,490, key="apricot", value=30, next=null)

Step 6: Link to existing chain
newNode.next = table[4]  (newNode points to "apple")
table[4] = newNode        (newNode becomes HEAD)

Result at index 4:
table[4] → Node("apricot", 30) → Node("apple", 10) → null
                    ↑                    ↑
                  HEAD                OLD HEAD

IMPORTANT: New entry goes at HEAD!
Why? Faster insertion (don't need to walk to end)
Also: Recently added items accessed more often (locality principle)
```

## 3.3 Retrieving from a Chain

### Getting an Entry When Chain Exists

```
map.get("apple")

Step 1: Calculate hash
hash("apple") = 94,225,460

Step 2: Calculate index
index = 94,225,460 % 16 = 4

Step 3: Get the chain at index 4
current = table[4]

table[4] contains:
Node("apricot", 30) → Node("apple", 10) → null

Step 4: Walk the chain
Comparison 1:
  current = Node("apricot", 30)
  Does hash match? 94,225,490 == 94,225,460? NO
  Does key match? "apricot" == "apple"? NO
  Move to next: current = current.next

Comparison 2:
  current = Node("apple", 10)
  Does hash match? 94,225,460 == 94,225,460? YES ✓
  Does key match? "apple" == "apple"? YES ✓
  FOUND! Return 10

Result: 10
```

### Two-Part Equality Check

```
Why check BOTH hash AND key.equals()?

If we only checked key.equals():
✓ Would work
✗ But equals() can be slow (string comparison, field by field)

If we only checked hash:
✓ Faster (integer comparison)
✗ Could have hash collision! Different keys, same hash

Java's approach:
1. First check hash (fast primitive comparison)
   if (hash1 == hash2) {
2. Then check key (thorough object comparison)
       if (key1.equals(key2)) {
           FOUND!
       }
   }

This is FASTER than just equals()!
(Most comparisons fail at hash check)
```

### Finding Entry NOT in Map

```
map.get("missing")

Step 1: Calculate hash
hash("missing") = X

Step 2: Calculate index
index = X % 16 = 2

Step 3: Get chain at index 2
current = table[2]

table[2] contains: null

Step 4: Walk chain
while (current != null) {  ← current is null, condition false
    ...
}

Exit loop immediately!

Result: null (entry not found)

TIME: O(1) because bucket was empty!
Even if "missing" didn't exist, still fast!
```

---

# PART 4: UNDERSTANDING LOAD FACTOR

## 4.1 What is Load Factor?

### Definition

```
Load Factor (α) = Total Entries / Capacity

Example 1:
Capacity: 16
Entries: 12
Load Factor: 12/16 = 0.75

Example 2:
Capacity: 32
Entries: 8
Load Factor: 8/32 = 0.25

Example 3:
Capacity: 64
Entries: 63
Load Factor: 63/64 = 0.98

Load factor tells you: "How full is my HashMap?"
```

### What Load Factor Means for Performance

```
Load Factor α directly affects average chain length!

With GOOD hash function:
Average chain length = Load Factor (α)

Example:
Capacity: 16
Entries: 12
Load Factor: 0.75

Expected average chain length: 0.75 entries per bucket
Meaning: Most buckets have 0 or 1 entry
Some have 2, few have 3

Average GET operation: check ~0.75 entries
Performance: Still O(1)!

Compare with:
Capacity: 16
Entries: 100
Load Factor: 6.25

Average chain length: 6.25 entries
Average GET operation: check ~6 entries
Performance: Degraded to O(6) = O(n)!
```

### Visual Representation

```
Load Factor = 0.25 (underutilized):
Capacity: 16
Entries: 4

Hash table:
[0]: Entry
[1]: null
[2]: null
[3]: Entry
[4]: null
[5]: null
...
[15]: Entry

Most buckets empty!
✓ Fast lookups (avg 1.25 entries)
✗ Wasted memory (75% empty)


Load Factor = 0.75 (balanced):
Capacity: 16
Entries: 12

Hash table:
[0]: Entry → Entry
[1]: Entry
[2]: Entry → Entry
[3]: null
[4]: Entry → Entry
[5]: Entry
[6]: null
...

Occupied buckets: 12
Empty buckets: 4
✓ Fast lookups (avg 1.75 entries)
✓ Reasonable memory usage
✓ Sweet spot!


Load Factor = 0.99 (overstuffed):
Capacity: 16
Entries: 15

Hash table:
[0]: Entry → Entry
[1]: Entry → Entry → Entry
[2]: Entry → Entry
[3]: Entry
...
[15]: Entry

Almost all buckets occupied!
✗ Slow lookups (long chains)
✓ Memory efficient
✗ Need to resize soon!
```

## 4.2 Why Resize at 0.75?

### The Trade-off Problem

```
Two competing goals:
1. FAST operations (short chains)
2. EFFICIENT memory (use available space)

These conflict!

Fast operations:
Need low load factor (few entries per bucket)
Requires large capacity
Wastes memory!

Efficient memory:
Use high load factor (full buckets)
Requires small capacity
Slow operations!

The question: What's the BEST compromise?
```

### Mathematical Analysis

```
Expected cost of GET operation:
Cost = O(1 + α)
where α = load factor

Example:
α = 0.5:  Cost = 1.5 comparisons
α = 0.75: Cost = 1.75 comparisons
α = 0.99: Cost = 1.99 comparisons

Memory cost:
Memory = O(size/α)

Example with 1000 entries:
α = 0.25: Need 4000 capacity = 4000 * size_per_entry
α = 0.75: Need 1333 capacity = 1333 * size_per_entry
α = 0.99: Need 1010 capacity = 1010 * size_per_entry

Total cost = Time cost + Memory cost

Optimization:
Find α that minimizes total cost

Result: α = 0.75 is OPTIMAL
(Proven mathematically!)
```

### Why NOT 0.5 or 0.9?

```
Load Factor = 0.5:
Capacity = 2 × size
✓ Very fast (avg 1.5 entries)
✗ Uses 2× memory (wastes half!)

Resizing frequently:
Size 8 → resize to 16
Size 16 → resize to 32
Size 32 → resize to 64
Lots of resize operations!

Load Factor = 0.9:
Capacity = 1.11 × size
✓ Memory efficient
✗ Slower (avg 1.9 entries)
Chain collisions get bad

Load Factor = 0.75:
Capacity = 1.33 × size
✓ Fast (avg 1.75 entries)
✓ Reasonable memory (1/3 extra)
✓ Balanced!

This is Java's default choice!
```

---

# PART 5: RESIZING - WHY AND HOW

## 5.1 Why Resize?

### The Problem as HashMap Fills

```
Start:
Capacity: 16
Threshold: 12 (0.75 * 16)

Adding entries:
Size 1: α = 1/16 = 0.06 (avg chain 0.06)
Size 5: α = 5/16 = 0.31 (avg chain 0.31)
Size 10: α = 10/16 = 0.62 (avg chain 0.62)
Size 12: α = 12/16 = 0.75 (avg chain 0.75) ← THRESHOLD!

After 12 entries: Must resize!
Size 13: If we don't resize, α = 13/16 = 0.81
         Average chain becomes 1.81 entries
         Performance degrading!

Resize to capacity 32:
Size 13: α = 13/32 = 0.41 (avg chain 0.41)
Performance restored!
```

### Visualization

```
WITHOUT RESIZE:
As size increases, chains get longer, performance degrades

Size:  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16
α:     0.06 0.12 0.19 0.25 0.31 0.38 0.44 0.5 0.56 0.62 0.69 0.75 0.81 0.88 0.94 1.0
Perf:  O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1) O(1.2) O(1.3) ... O(2)
       ████████████ DEGRADING ████████████→ VERY SLOW

WITH RESIZE:
At size 12 → resize to 32
Chains reset to short, performance maintained

Size:  1  2  3  4  5  6  7  8  9 10 11 12 |RESIZE| 13 14... 24
α:     0.06-0.75                 ████████  32 capacity ████████  0.75
Perf:  O(1) ─────────────────    RESET TO O(1) ─────────────────
       (capacity 16)         ↓↓↓  (capacity 32)
```

## 5.2 The Resize Process

### Step-by-Step

```
Initial State (Capacity 16):
Threshold: 12
Size: 12
Entries spread across buckets:

Index 0: Entry("a")
Index 1: null
Index 2: Entry("b") → Entry("c")
Index 3: null
Index 4: Entry("d") → Entry("e") → Entry("f")
Index 5: Entry("g")
...

Size reaches 12 (threshold) → TRIGGER RESIZE

Step 1: Create new table
Old capacity: 16
New capacity: 16 * 2 = 32
New table created with 32 empty slots

Step 2: Update threshold
New threshold: 32 * 0.75 = 24

Step 3: Rehash all entries
This is CRUCIAL to understand!
```

### Rehashing Explained

```
Rehashing: Recalculating index for each entry with NEW capacity

Old capacity: 16
Entry has hash: 94,225,460

Old index: 94,225,460 & (16-1) = 94,225,460 & 15

New capacity: 32
New index: 94,225,460 & (32-1) = 94,225,460 & 31

THE INDEX CHANGES!

Why?
16 - 1 = 15 = 0b00001111 (last 4 bits)
32 - 1 = 31 = 0b00011111 (last 5 bits)

With 5 bits, we get different result than 4 bits!

Example:
hash = 0b...11010100

Old index (& with 0b1111):
hash & 0b1111 = 0b0100 = 4

New index (& with 0b11111):
hash & 0b11111 = 0b10100 = 20

Same hash!
Different indices! (4 vs 20)
```

### What Happens to Chains During Resize

```
Before resize:
Index 4: Entry(hash=94225460, "apple") → Entry(hash=94225472, "apricot") → null

Chain length: 2 (collision!)

After rehashing with new capacity:
Entry "apple":
  hash = 94225460
  old index = 94225460 & 15 = 4
  new index = 94225460 & 31 = ?
  Let's say: 4 (stays in same bucket)
  
Entry "apricot":
  hash = 94225472
  old index = 94225472 & 15 = 4
  new index = 94225472 & 31 = ?
  Let's say: 20 (moves to different bucket!)

Result:
Old table index 4: Entry → Entry → null (chain of 2)

New table:
Index 4: Entry("apple") → null (chain of 1)
Index 20: Entry("apricot") → null (chain of 1)

THE CHAIN BROKE APART!
Collisions reduced!
Performance improved!
```

## 5.3 Code Walk-Through of Resize

Let me show you what resize code does step by step:

```java
void resize() {
    // Step 1: Save old table and calculate new capacity
    Node<K,V>[] oldTable = table;  // Keep reference to old table
    int newCapacity = oldTable.length * 2;  // Double the size
    
    // Step 2: Create new empty table
    table = new Node[newCapacity];
    // Now table points to new array
    // Size: 32 (if was 16)
    
    // Step 3: Update threshold
    threshold = (int)(newCapacity * 0.75f);
    // If new capacity 32: threshold = 24
    
    // Step 4: Rehash every entry
    // Walk through old table and move entries
    
    for (Node<K,V> entry : oldTable) {
        // entry is reference to chain at each index
        
        while (entry != null) {
            // entry is one node in the chain
            
            // Calculate NEW index for this entry
            int newIndex = entry.hash & (newCapacity - 1);
            // Using new capacity to calculate!
            
            // Save the next node (we'll lose it when we change .next)
            Node<K,V> next = entry.next;
            
            // Insert this entry at the HEAD of new table
            entry.next = table[newIndex];  // Point to old chain
            table[newIndex] = entry;       // Become new head
            
            // Move to next entry in old chain
            entry = next;
        }
    }
}

// Let me trace through with actual example:

oldTable:
[0]: null
[1]: null
[2]: Entry(h="b") → Entry(h="c") → null
[3]: null
[4]: Entry(h="d") → Entry(h="e") → null
...

Create new empty table of size 32

Process oldTable[2]:
  Entry "b": newIndex = hash("b") & 31 = 10
             table[10] = Entry("b"), table[10].next = null
  
  Entry "c": newIndex = hash("c") & 31 = 18
             table[18] = Entry("c"), table[18].next = null
             
Process oldTable[4]:
  Entry "d": newIndex = hash("d") & 31 = 4
             table[4] = Entry("d"), table[4].next = null
  
  Entry "e": newIndex = hash("e") & 31 = 20
             table[20] = Entry("e"), table[20].next = null

After rehashing:
oldTable[2] chain broke apart:
new_table[10]: Entry("b")
new_table[18]: Entry("c")

oldTable[4] chain broke apart:
new_table[4]: Entry("d")
new_table[20]: Entry("e")

Result: Better distribution!
No more long chains!
```

---

# PART 6: COMPLETE PUT OPERATION

## 6.1 Full PUT Example with All Steps

Let me walk you through a complete PUT operation showing every decision:

```
HashMap.put("java", 2024)

Current state before put:
capacity: 16
size: 11
threshold: 12
table[3]: null
table[7]: Entry("python", 2020) → null
table[10]: Entry("javascript", 2024) → null

═══════════════════════════════════════════════════════════════

STEP 1: CHECK IF RESIZE NEEDED

Code:
if (size >= threshold) {
    resize();
}

Check:
size >= threshold?
11 >= 12?
NO

So NO RESIZE NOW.
(Will be checked again later if we add entry)

═══════════════════════════════════════════════════════════════

STEP 2: COMPUTE HASH

Code:
int hash = hash(key);

What happens:
hash("java") = ?

Step 2.1: Call hashCode()
"java".hashCode() = 3254818

Step 2.2: Apply HashMap's hash function (spread bits)
h = 3254818
h >>> 16 = 3254818 >> 16 = 49
final hash = 3254818 XOR 49 = 3254867

So hash = 3254867

Why spread bits? Makes distribution more uniform.

═══════════════════════════════════════════════════════════════

STEP 3: CALCULATE INDEX

Code:
int index = hash & (capacity - 1);

Calculation:
hash = 3254867
capacity = 16
capacity - 1 = 15 = 0b00001111

index = 3254867 & 0b00001111
      = 0b...1011 & 0b1111
      = 0b1011
      = 11

Index = 11

═══════════════════════════════════════════════════════════════

STEP 4: GET CHAIN AT THAT INDEX

Code:
Node<K,V> node = table[index];

Check:
table[11] = ?

Looking at our table:
table[11] is null

So node = null

═══════════════════════════════════════════════════════════════

STEP 5: SEARCH FOR EXISTING KEY IN CHAIN

Code:
while (node != null) {
    if (node.hash == hash && 
        (node.key == key || node.key.equals(key))) {
        V oldValue = node.value;
        node.value = value;
        return oldValue;
    }
    node = node.next;
}

Execution:
node = null

while (null != null) → condition is FALSE
Loop never executes!

Conclusion: Key "java" NOT FOUND in chain
           (because chain is empty)

═══════════════════════════════════════════════════════════════

STEP 6: ADD NEW ENTRY

Code:
addEntry(hash, key, value, index);

private void addEntry(int hash, K key, V value, int index) {
    Node<K,V> newNode = new Node<>(
        hash,          // 3254867
        key,           // "java"
        value,         // 2024
        table[index]   // null (current chain at index 11)
    );
    table[index] = newNode;  // newNode becomes HEAD
    size++;
}

Execution:
Create new Node:
Node {
    hash: 3254867
    key: "java"
    value: 2024
    next: null
}

Set table[11] to this node:
table[11] = Node("java", 2024)

Increment size:
size = 11 + 1 = 12

═══════════════════════════════════════════════════════════════

FINAL STATE AFTER PUT:

size: 12
table[3]: null
table[7]: Entry("python", 2020) → null
table[10]: Entry("javascript", 2024) → null
table[11]: Entry("java", 2024) → null

Next put() will trigger resize because size (12) >= threshold (12)

═══════════════════════════════════════════════════════════════

WHAT IF THERE WAS A COLLISION?

Scenario: We put "scripting" instead, which also hashes to index 11

PUT("scripting", 2010) with existing Entry("java", 2024) at index 11

Step 1: Check resize
size >= 12? Yes, but let's ignore and show collision

Step 2-3: hash("scripting") = ... → index 11

Step 4: Get chain at index 11
node = table[11] = Entry("java", 2024) → null

Step 5: Search chain
while (Entry("java", 2024) != null):
  node.hash == hash("scripting")? NO
  node.key.equals("scripting")? NO
  node = node.next = null

Chain exhausted, key not found

Step 6: Add new entry
newNode = Node(hash("scripting"), "scripting", 2010, table[11])
                                                   ↑
                                          Points to Entry("java")!

table[11] = newNode

Result:
table[11] → Entry("scripting", 2010) → Entry("java", 2024) → null
            ↑
           HEAD (newly added)

Both entries in SAME bucket!
One chain with 2 entries.

GET "java": Must walk chain, check 1st entry, then find at 2nd position
Time: O(2) = O(1) still manageable
```

---

# PART 7: COMPLETE GET OPERATION

## 7.1 GET with Different Scenarios

### Scenario 1: Key Found Immediately

```
table[10]: Entry("javascript", 2024) → Entry("typescript", 2024) → null

GET("javascript")

Step 1: hash("javascript") = X
Step 2: index = X & 15 = 10
Step 3: node = table[10] = Entry("javascript", 2024)
Step 4: Check first node
        node.hash == hash("javascript")? YES ✓
        node.key.equals("javascript")? YES ✓
        FOUND! Return 2024

Comparisons: 1
Time: O(1)
```

### Scenario 2: Key Found After Walking Chain

```
table[7]: Entry("python", 2020) → Entry("perl", 1987) → null

GET("perl")

Step 1: hash("perl") = X
Step 2: index = X & 15 = 7
Step 3: node = table[7] = Entry("python", 2020)
Step 4: Check chain
        
Iteration 1:
node = Entry("python", 2020)
node.hash == hash("perl")? NO
node.key.equals("perl")? NO
node = node.next = Entry("perl", 1987)

Iteration 2:
node = Entry("perl", 1987)
node.hash == hash("perl")? YES ✓
node.key.equals("perl")? YES ✓
FOUND! Return 1987

Comparisons: 2 (checked python, then perl)
Time: O(2) = O(1) still (small constant)
```

### Scenario 3: Key Not Found - Empty Bucket

```
table[5]: null

GET("missing")

Step 1: hash("missing") = X
Step 2: index = X & 15 = 5
Step 3: node = table[5] = null
Step 4: while (null != null) → FALSE, loop doesn't execute
Step 5: return null

Comparisons: 0
Time: O(1)

Very fast! Didn't even walk a chain!
```

### Scenario 4: Key Not Found - Non-Empty Chain

```
table[4]: Entry("go", 2009) → Entry("rust", 2010) → null

GET("swift")  ← Doesn't exist, but hashes to index 4

Step 1: hash("swift") = X
Step 2: index = X & 15 = 4
Step 3: node = table[4] = Entry("go", 2009)
Step 4: Check chain

Iteration 1:
node = Entry("go", 2009)
node.hash == hash("swift")? NO
node.key.equals("swift")? NO
node = node.next = Entry("rust", 2010)

Iteration 2:
node = Entry("rust", 2010)
node.hash == hash("swift")? NO
node.key.equals("swift")? NO
node = node.next = null

Iteration 3:
node = null
while (null != null) → FALSE, exit loop

return null

Comparisons: 2 (checked go and rust, even though neither matched)
Time: O(2) = O(1) still

Key learning: Even when key not found,
              if chain is short, still O(1)!
              Only slow if chain is VERY long.
```

## 7.2 Why Two-Part Equality Check Matters

```
Two-part check:
if (node.hash == hash && 
    (node.key == key || node.key.equals(key)))

Why not just: if (node.key.equals(key))?

Because equals() can be SLOW!

Example:
key = new String("java");
String in node = new String("java");
key.equals(node.key):
  Must compare each character!
  'j' == 'j'? 'a' == 'a'? 'v' == 'v'? 'a' == 'a'?
  For long strings, takes time

Solution:
First check hashes (single integer comparison):
hash1 == hash2?
  Fast CPU operation (~1 cycle)
  If FALSE, definitely not equal → skip equals()
  
Only if hashes match, call equals():
key.equals(node.key)
  Slower, but rarely executed
  
Result: Faster overall!
```

---

# PART 8: CUSTOM KEYS - CRITICAL FOR INTERVIEWS

## 8.1 What Makes a Good Key?

### Requirement 1: Immutable

```
What is immutable?
= Cannot be changed after creation

Good key (String):
String name = "java";
// Can't change name afterwards
name.substring(0, 2);  // Returns new string, doesn't modify
name.toUpperCase();    // Returns new string, doesn't modify

Original "java" remains unchanged!

Bad key (StringBuilder):
StringBuilder sb = new StringBuilder("java");
sb.append("script");  // MODIFIES the object!
sb.setCharAt(0, 'J'); // MODIFIES the object!

Now it contains "JAVAscript"!
```

### Why Immutability Matters for HashMap

```
Critical example:

MutableKey key = new MutableKey("apple");
map.put(key, 100);

At this moment:
hash(key) = H1 (calculated from "apple")
index = H1 & 15 = 4
Stored at: table[4]

Now someone modifies key:
key.setData("grape");

What happens?
hash(key) = H2 (calculated from "grape")
H1 != H2 (different!!)

If we try: map.get(key)
hash(key) = H2 (because data is "grape")
index = H2 & 15 = 8
Looks at table[8]
Entry was at table[4]!
NOT FOUND!

But we JUST PUT IT!
map.get(key) returns null

CORRUPTION!

Solution:
Make keys IMMUTABLE!
"""class GoodKey {
    private final String data;  // final = can't reassign
    
    public GoodKey(String data) {
        this.data = data;
    }
    
    // NO setters that modify!
}
"""
Now key can never change!
Hash will never change!
HashMap will work correctly!
```

### Requirement 2: Implement hashCode() and equals()

```
Contract (MUST be true):
If a.equals(b) is true,
then a.hashCode() == b.hashCode()

Example:
Person p1 = new Person("john", 25);
Person p2 = new Person("john", 25);

p1.equals(p2)? YES (same name and age)
p1.hashCode() == p2.hashCode()? MUST BE YES!

If not:
HashMap would treat them as different keys
Even though logically they're the same!

When to implement:
Any custom class used as key in HashMap

How to implement:
Use all fields that define equality

class Person {
    String name;
    int age;
    String city;  // Different from other field
    
    @Override
    public int hashCode() {
        // Use BOTH name and age (what defines equality)
        // Don't use city (what makes difference)
        return Objects.hash(name, age);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) return false;
        Person other = (Person) o;
        return this.name.equals(other.name) &&
               this.age == other.age;
        // Two people same name/age = same person
        // city doesn't matter
    }
}

Now:
Person p1 = new Person("john", 25, "NY");
Person p2 = new Person("john", 25, "LA");

p1.equals(p2)? YES
p1.hashCode() == p2.hashCode()? YES

map.put(p1, "person1");
map.get(p2)?  Returns "person1"
Correct!
```

---

# INTERVIEW TIPS FOR EACH CONCEPT

## 8.2 How to Answer Interview Questions

### When Asked: "Explain HashMap Internal Working"

```
Structure your answer:

1. START WITH THE PROBLEM
   "HashMap solves the problem of fast key-value lookup.
    Instead of searching through array (O(n)),
    HashMap uses hash function to go directly to entry (O(1))."

2. EXPLAIN HASH FUNCTION
   "Hash function converts any key (String, Integer, etc.)
    to an integer index.
    Same key always gives same index (deterministic).
    This allows direct access."

3. EXPLAIN ARRAY & CHAINING
   "HashMap is array of Node objects.
    Each Node has: hash, key, value, next pointer.
    When two keys hash to same index (collision),
    we chain them in linked list."

4. EXPLAIN PUT OPERATION
   "PUT does:
    1. Calculate hash and index
    2. Walk chain at that index
    3. If key exists: update value
    4. If key doesn't exist: add at head of chain
    5. If size >= threshold: resize"

5. EXPLAIN PERFORMANCE
   "Average case: O(1) because chain length is short
    Worst case: O(n) if all entries hash to same bucket
    Java 8+: O(log n) because tree converts long chains"

This shows complete understanding!
```

### When Asked: "What is Load Factor?"

```
Simple explanation:
"Load factor is the ratio of entries to capacity.
 When size > capacity * 0.75, resize happens.

 Why 0.75? It's the mathematical optimal point.
 - Lower (0.5): Fast but wastes memory
 - Higher (0.9): Memory efficient but slow
 - 0.75: Best balance"

Add example:
"If capacity is 16 and load factor is 0.75,
 at size 12, we resize to capacity 32.
 This keeps chains short and maintains O(1) performance."
```

### When Asked: "Why Must Keys Be Immutable?"

```
Real example answer:
"If I create key = new MutableKey('apple'),
 put it in HashMap at table[4] (based on hash('apple')).

 Later someone calls key.setData('grape').
 Now hash(key) is different!
 But entry is still at table[4].

 When I try to get(key), it looks at wrong bucket.
 Returns null even though we stored it.

 Solution: Make key immutable (final, no setters).
 Then hash never changes, HashMap works correctly."

Follow up:
"Good keys: String, Integer, UUID (all immutable)
 Bad keys: StringBuilder, ArrayList (all mutable)"
```

---

CONTINUE IN NEXT PART...

# SUMMARY SO FAR

You now understand:

```
✓ Why HashMap was created (O(1) vs O(n) problem)
✓ What hash function does and why it matters
✓ How collisions happen and why chaining solves them
✓ Load factor and why 0.75 is optimal
✓ Resize and rehashing process
✓ Complete PUT operation step-by-step
✓ Complete GET operation with examples
✓ Why keys must be immutable
✓ How to implement hashCode() and equals()
✓ How to answer interview questions
```

These are the FUNDAMENTALS that every interview question builds on!

Before moving to advanced topics (trees, concurrency, etc.),
make sure you can:

1. Trace a PUT operation on paper without IDE
2. Trace a GET operation and find the value
3. Explain collision handling with examples
4. Explain load factor and when resize happens
5. Explain why keys must be immutable
6. Implement hashCode() and equals() correctly

Practice these until they're automatic!

Then we'll cover Java 8+ optimizations, thread safety, and advanced interview questions.
