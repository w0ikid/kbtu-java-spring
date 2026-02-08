create table users (
  id bigserial primary key,
  username text not null,
  email text not null unique,
  password text not null,
  created_at timestamptz not null default now()
);

create table pets (
  id bigserial primary key,
  name text not null,
  type text not null,
  owner_id bigint references users(id),
  created_at timestamptz not null default now()
);