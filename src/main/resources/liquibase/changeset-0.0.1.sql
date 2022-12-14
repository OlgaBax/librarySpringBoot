--liquibase formatted sql

--changeset Bainc:001

create table public.genres(id bigint generated by default as identity primary key not null,
                           title varchar(100));
--changeset Bainc:002
create table public.pubhouses(id bigint generated by default as identity primary key not null,
                              title varchar(100));
--changeset Bainc:003
create table public.tags(id bigint generated by default as identity primary key not null,
                         title varchar(100));
--changeset Bainc:004
create table public.authors(id bigint generated by default as identity primary key not null,
                            surname varchar(25),
                            name varchar(25),
                            middlename varchar(25));
--changeset Bainc:005
create table public.books(id bigint generated by default as identity primary key not null,
                          uuid uuid unique not null,
                          title varchar(100),
                          originaltitle varchar(100),
                          yearedition varchar(25),
                          isbnoriginal varchar(25),
                          isbntranslate varchar(25),
                          yearofeditiontranslate varchar(25),
                          fileformatbook integer,
                          pathtozipbook varchar(255),
                          genre_id bigint,
                          pubhouse_id bigint,
                          pubhousetranslate_id bigint);

--changeset Bainc:006
create table public.books_authors(book_id bigint not null, author_id bigint not null,
                                  constraint books_id foreign key(book_id) references public.books(id),
                                  constraint authors_id foreign key(author_id) references public.authors(id));

--changeset Bainc:007
create table public.books_tags(book_id bigint not null, tag_id bigint not null,
                                  constraint books_id foreign key(book_id) references public.books(id),
                                  constraint tags_id foreign key(tag_id) references public.tags(id));

--changeset Bainc:008
alter table public.books add constraint fk_book_genre foreign key (genre_id) references public.genres(id);

--changeset Bainc:009
alter table public.books add constraint fk_book_pubHouse foreign key (pubHouse_id) references public.pubhouses(id);

--changeset Bainc:010
create table public.users(id bigint generated by default as identity primary key not null,
                          status varchar(25) not null default 'ACTIVE',
                          username varchar(100) unique not null,
                          firstname varchar(100),
                          lastname varchar(100),
                          password varchar(255) not null);

--changeset Bainc:011
create table public.roles(id bigint generated by default as identity primary key not null,
                          status varchar not null default 'ACTIVE',
                          name varchar not null unique);

--changeset Bainc:012
create table public.user_roles(user_id bigint not null, role_id bigint not null,
                                constraint users_id foreign key(user_id) references public.users(id),
                                constraint roles_id foreign key(role_id) references public.roles(id));
--changeset Bainc:013
insert into public.roles(name) values ('ROLE_USER'),('ROLE_ADMIN');

--changeset Bainc:014
insert into public.users(username,firstname,lastname,password) values ('user','Olga','Bax','user');

--changeset Bainc:015
insert into public.user_roles(user_id,role_id) values (1,2);


