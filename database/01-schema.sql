-- Player
CREATE TABLE player
(
    id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    -- Basic info
    name          VARCHAR(15)              NOT NULL UNIQUE,
    mail          VARCHAR(255)             NOT NULL UNIQUE,
    password      VARCHAR(128)             NOT NULL,
    ip            INET,

    -- Appearance
    char          JSONB                    NOT NULL, -- Stores serialized appearance

    -- Original character appearance
    original_char JSONB,

    -- Mimetized character appearance
    mim_char      JSONB,

    -- Privileges
    privileges    INT                      NOT NULL DEFAULT 0,

    description   VARCHAR(100)                      DEFAULT '',
    desc_rm       VARCHAR(100)                      DEFAULT '',

    -- Archetype, race and home
    archetype     INT                      NOT NULL,
    race          INT                      NOT NULL,
    gender        INT                      NOT NULL,
    home          INT                      NOT NULL,

    -- Position
    x             INT                      NOT NULL,
    y             INT                      NOT NULL,
    map           INT                      NOT NULL,

    -- Pets
    pet           JSONB,

    -- Groups TODO: not yet
    --guild_id      INT REFERENCES guilds (id),
    --party_id      INT REFERENCES parties (id),

    -- Timestamps
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--indexes for guild/party
CREATE UNIQUE INDEX idx_player_name ON player (LOWER(name));
CREATE UNIQUE INDEX idx_player_mail ON player (LOWER(mail));

CREATE TABLE player_inventory
(
    player_id   INT     NOT NULL REFERENCES player (id) ON DELETE CASCADE,

    slot_index  INT     NOT NULL,
    obj_id      INT     NOT NULL,
    quantity    INT     NOT NULL DEFAULT 0,
    is_equipped BOOLEAN NOT NULL DEFAULT FALSE,
    is_bank     BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY (player_id, slot_index, is_bank)
);

CREATE TABLE player_stats
(
    player_id         INT    NOT NULL REFERENCES player (id) ON DELETE CASCADE,

    -- Currency
    gold              BIGINT NOT NULL DEFAULT 0,
    bank_gold         BIGINT NOT NULL DEFAULT 0,

    -- Stats
    max_hp            INT    NOT NULL DEFAULT 0,
    min_hp            INT    NOT NULL DEFAULT 0,
    max_mp            INT    NOT NULL DEFAULT 0,
    min_mp            INT    NOT NULL DEFAULT 0,
    max_sta           INT    NOT NULL DEFAULT 0,
    min_sta           INT    NOT NULL DEFAULT 0,
    max_hit           INT    NOT NULL DEFAULT 0,
    min_hit           INT    NOT NULL DEFAULT 0,

    max_hunger        INT    NOT NULL DEFAULT 0,
    min_hunger        INT    NOT NULL DEFAULT 0,

    max_thirst        INT    NOT NULL DEFAULT 0,
    min_thirst        INT    NOT NULL DEFAULT 0,

    defense           INT    NOT NULL DEFAULT 0,
    exp               BIGINT NOT NULL DEFAULT 0,
    elv               INT    NOT NULL DEFAULT 0,
    elu               INT    NOT NULL DEFAULT 0,

    -- Skill points
    skills            INT[],

    -- Skill progressions
    exp_skills        INT[],
    elu_skills        INT[],

    -- User attributes
    attributes        INT[],
    attributes_backup INT[],

    -- Spells
    spells            INT[],

    -- Counters (TODO: move)
    users_killed      INT             DEFAULT 0,
    criminals_killed  INT             DEFAULT 0,
    npcs_killed       INT             DEFAULT 0,
    skill_points      INT             DEFAULT 0
);

CREATE TABLE player_flags
(
    player_id      INT  NOT NULL REFERENCES player (id) ON DELETE CASCADE,

    is_dead        BOOL NOT NULL DEFAULT FALSE,
    is_trading     BOOL NOT NULL DEFAULT FALSE,
    --is_logged BOOL NOT NULL DEFAULT FALSE,
    hunger         BOOL NOT NULL DEFAULT FALSE,
    thirst         BOOL NOT NULL DEFAULT FALSE,
    --canMove, timerLanzarSpell, canWork
    is_poisoned    BOOL NOT NULL DEFAULT FALSE,
    is_paralyzed   BOOL NOT NULL DEFAULT FALSE,
    is_immobilized BOOL NOT NULL DEFAULT FALSE,
    is_dumb        BOOL NOT NULL DEFAULT FALSE,
    is_blind       BOOL NOT NULL DEFAULT FALSE,
    is_invisible   BOOL NOT NULL DEFAULT FALSE,
    is_cursed      BOOL NOT NULL DEFAULT FALSE,
    is_hidden      BOOL NOT NULL DEFAULT FALSE,
    is_naked       BOOL NOT NULL DEFAULT FALSE,
    is_resting     BOOL NOT NULL DEFAULT FALSE,
    --spell INT DEFAULT 0,
    potion_drank   BOOL NOT NULL DEFAULT FALSE,
    potion_type    INT  NOT NULL DEFAULT 0,
    --attackable, attackable_by, shareNpcWith
    flies          BOOL NOT NULL DEFAULT FALSE,
    navigating     BOOL NOT NULL DEFAULT FALSE,
    --effect_duration (this is not a flag)
    --targetnpc,targetnpctype,ownednpc,npcinv

    banned         BOOL NOT NULL DEFAULT FALSE,
    admin_banned   BOOL NOT NULL DEFAULT FALSE,
    --targetUser, targetObj, targetObjMapXY, targetMapXY
    --targetObjInvIndex, targetObjInvSlot
    --attackedByNpc, attackedByUser, attackedNpc
    ignored        BOOL NOT NULL DEFAULT FALSE,

    --in_consultation BOOL NOT NULL DEFAULT FALSE, SEND_DENOUNCES
    --statschanged
    --lkcrim, lkciti, oldbody,olhead, admininvi, adminchaseable

    chat_color     INT  NOT NULL DEFAULT 0,
    times_walk     INT  NOT NULL DEFAULT 0, --TODO: counter
    start_walk     INT  NOT NULL DEFAULT 0,
    count_sh       INT  NOT NULL DEFAULT 0,

    --lastmessage
    silenced BOOL NOT NULL DEFAULT FALSE,
    mimetized BOOL NOT NULL DEFAULT FALSE

    --sentinelindex, sentinelok, lastMap, traveling, paralyzedby,
    --paralidezibyindex, paralizedbynpcindex
);