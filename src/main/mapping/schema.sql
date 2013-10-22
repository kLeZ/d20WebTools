
    alter table d20webtools.MESSAGES 
        drop 
        foreign key FK131AF14CEFD6CFE4;

    alter table d20webtools.MESSAGES 
        drop 
        foreign key FK131AF14CF00C43A4;

    alter table d20webtools.MESSAGES 
        drop 
        foreign key FK131AF14CF00F1984;

    alter table d20webtools.ROOMS 
        drop 
        foreign key FK4A8A2D8AF65581B;

    alter table d20webtools.USERS 
        drop 
        foreign key FK4D495E8EFD6CFE4;

    drop table if exists d20webtools.MESSAGES;

    drop table if exists d20webtools.ROOMS;

    drop table if exists d20webtools.USERS;

    create table d20webtools.MESSAGES (
        ID bigint not null,
        TIME datetime,
        USER bigint,
        TEXT varchar(255),
        ROOM integer,
        primary key (ID)
    );

    create table d20webtools.ROOMS (
        ID integer not null,
        NAME varchar(255),
        MASTER bigint,
        primary key (ID)
    );

    create table d20webtools.USERS (
        ID bigint not null,
        NAME varchar(255),
        EMAIL varchar(255),
        PASSWORD varchar(255),
        primary key (ID)
    );

    alter table d20webtools.MESSAGES 
        add index FK131AF14CEFD6CFE4 (ID), 
        add constraint FK131AF14CEFD6CFE4 
        foreign key (ID) 
        references d20webtools.ROOMS (ID);

    alter table d20webtools.MESSAGES 
        add index FK131AF14CF00C43A4 (ROOM), 
        add constraint FK131AF14CF00C43A4 
        foreign key (ROOM) 
        references d20webtools.ROOMS (ID);

    alter table d20webtools.MESSAGES 
        add index FK131AF14CF00F1984 (USER), 
        add constraint FK131AF14CF00F1984 
        foreign key (USER) 
        references d20webtools.USERS (ID);

    alter table d20webtools.ROOMS 
        add index FK4A8A2D8AF65581B (MASTER), 
        add constraint FK4A8A2D8AF65581B 
        foreign key (MASTER) 
        references d20webtools.USERS (ID);

    alter table d20webtools.USERS 
        add index FK4D495E8EFD6CFE4 (ID), 
        add constraint FK4D495E8EFD6CFE4 
        foreign key (ID) 
        references d20webtools.ROOMS (ID);
