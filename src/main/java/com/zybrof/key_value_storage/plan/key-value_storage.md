# Solutions

## Separation

### CAP Theory

- Consistency
- Availability
- Partition Tolerance

### Load balancing

Consistency hashing

### Read/write balancing

#### Quorum

- ACK - Acknowledge or confirm
- N - total number of nodes
- W - min number of ACK on writing
- R - min number of ACK on reading

W + R > N - Strong consistency

### Detecting failed nodes

#### Gossip protocol

```
*->2 [0, 0, 1, 0]
2->1 [0, 1, 1, 0]
1->0 [1, 1, 1, 0!] ! where is 3-th server?
0->1 [1, 2, 1, 0!] !
1->2 [1, 2, 2, x]  x 3-th server is dead
```

### Detecting version conflicts

#### Vector clocks

```
[1, 2, 3] & [1, 3, 4] = 1<=1,  2<=3, 3<=4 = no conflict
[1, 2, 3] & [1, 1, 4] = 1<=1, !2<=1, 3<=4 = conflict
```
