CREATE TABLE devweek.person
(
    id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    name  text     not null,
    email text     not null
)