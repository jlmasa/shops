CREATE TABLE outbox (
    id UUID PRIMARY KEY,
    topic VARCHAR(100) NOT NULL,
    message_key VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    published BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX idx_outbox_pending ON outbox(created_at) WHERE published =
FALSE;
CREATE TABLE processed_events (
      event_id UUID PRIMARY KEY,
      processed_at TIMESTAMPTZ NOT NULL DEFAULT now()
);