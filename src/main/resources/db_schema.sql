create database social_network;

create table users(
    id serial primary key,
    username varchar(255) unique not null,
    password varchar(255) not null,
    information varchar(255) not null
);

create table users_followers(
    user_id integer not null references users(id) on delete cascade,
    follower_id integer not null references users(id) on delete cascade,
    constraint users_followers_pkey primary key(user_id, follower_id)
);

create table posts(
    id serial primary key,
    user_id integer not null references users(id) on delete cascade,
    title varchar(255) not null,
    description text not null,
    created timestamp without time zone not null
);
