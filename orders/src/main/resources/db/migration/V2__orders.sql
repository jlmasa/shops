CREATE TABLE orders (
    id UUID PRIMARY KEY,
    customer_id VARCHAR(60) NOT NULL,
    sku VARCHAR(40) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity >0),
    amount NUMERIC(12,2) NOT NULL,
    status VARCHAR(20)
)