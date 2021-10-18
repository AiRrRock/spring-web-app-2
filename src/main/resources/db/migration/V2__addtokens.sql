create table comments
(
    id         bigserial primary key,
    user_id    bigint references users (id),
    product_id bigint references products (id),
    comment      varchar(2550)
);

insert into comments (user_id, product_id, comment)
values (1,1,'JUST A comments'),
       (2,1,'OTHER comments');

