-- Lua script example for Redis
-- This script demonstrates a simple counter using Redis commands

-- Increment a counter
local counterKey = KEYS[1]                -- Key where the counter is stored
local incrementAmount = tonumber(ARGV[1]) -- Amount to increment the counter by

-- Check if the counter key exists
if redis.call("EXISTS", counterKey) == 0 then
  -- If key doesn't exist, set it to 0
  redis.call("SET", counterKey, 0)
end

-- Increment the counter
redis.call("INCRBY", counterKey, incrementAmount)

-- Return the updated counter value
return redis.call("GET", counterKey)
