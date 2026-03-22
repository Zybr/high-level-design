## UUID

The ID is unique and easy to generate.
But it's not sortable by time.

## Ticket Server

One server produces all IDs.
One point of failure. Or it's still tricky to manage as a distributed system.

## Snowflake

timestamp_datacenter-id_server-id_sequence
16_5_5_14
16+5+5+14 = 40 bits