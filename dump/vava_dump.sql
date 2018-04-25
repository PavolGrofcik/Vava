--
-- PostgreSQL database dump
--

-- Dumped from database version 10.2
-- Dumped by pg_dump version 10.2

-- Started on 2018-04-25 21:36:59

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 82352)
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE account (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    cash numeric DEFAULT 0,
    username character varying(25) NOT NULL,
    password character varying(30) NOT NULL
);


ALTER TABLE account OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 82350)
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE account_id_seq OWNER TO postgres;

--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 198
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE account_id_seq OWNED BY account.id;


--
-- TOC entry 197 (class 1259 OID 82341)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE customer (
    id integer NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    birth timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    sex character(1) NOT NULL,
    tel_number character varying(12),
    city character varying(25),
    address text,
    date character varying(255)
);


ALTER TABLE customer OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 82392)
-- Name: customer_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE customer_event (
    id integer NOT NULL,
    event_id integer NOT NULL,
    cust_id integer NOT NULL,
    specification boolean DEFAULT false
);


ALTER TABLE customer_event OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 82390)
-- Name: customer_event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_event_id_seq OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 204
-- Name: customer_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_event_id_seq OWNED BY customer_event.id;


--
-- TOC entry 196 (class 1259 OID 82339)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_id_seq OWNER TO postgres;

--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 196
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;


--
-- TOC entry 203 (class 1259 OID 82383)
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE event (
    id integer NOT NULL,
    location character varying(25) NOT NULL,
    e_start timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    length integer,
    height real,
    insurance boolean
);


ALTER TABLE event OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 82381)
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE event_id_seq OWNER TO postgres;

--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 202
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE event_id_seq OWNED BY event.id;


--
-- TOC entry 207 (class 1259 OID 82411)
-- Name: specification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE specification (
    id integer NOT NULL,
    customer_event_id integer NOT NULL,
    duration time without time zone,
    rank integer NOT NULL,
    length integer NOT NULL,
    finished boolean
);


ALTER TABLE specification OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 82409)
-- Name: specification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE specification_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE specification_id_seq OWNER TO postgres;

--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 206
-- Name: specification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE specification_id_seq OWNED BY specification.id;


--
-- TOC entry 201 (class 1259 OID 82369)
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE transaction (
    id integer NOT NULL,
    account_id integer NOT NULL,
    t_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    event_id integer NOT NULL
);


ALTER TABLE transaction OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 82367)
-- Name: transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE transaction_id_seq OWNER TO postgres;

--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 200
-- Name: transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE transaction_id_seq OWNED BY transaction.id;


--
-- TOC entry 2704 (class 2604 OID 82355)
-- Name: account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY account ALTER COLUMN id SET DEFAULT nextval('account_id_seq'::regclass);


--
-- TOC entry 2702 (class 2604 OID 82344)
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq'::regclass);


--
-- TOC entry 2710 (class 2604 OID 82395)
-- Name: customer_event id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_event ALTER COLUMN id SET DEFAULT nextval('customer_event_id_seq'::regclass);


--
-- TOC entry 2708 (class 2604 OID 82386)
-- Name: event id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY event ALTER COLUMN id SET DEFAULT nextval('event_id_seq'::regclass);


--
-- TOC entry 2712 (class 2604 OID 82414)
-- Name: specification id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY specification ALTER COLUMN id SET DEFAULT nextval('specification_id_seq'::regclass);


--
-- TOC entry 2706 (class 2604 OID 82372)
-- Name: transaction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction ALTER COLUMN id SET DEFAULT nextval('transaction_id_seq'::regclass);


--
-- TOC entry 2854 (class 0 OID 82352)
-- Dependencies: 199
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY account (id, customer_id, cash, username, password) FROM stdin;
3	1	1000	Paul	HelloWorld
\.


--
-- TOC entry 2852 (class 0 OID 82341)
-- Dependencies: 197
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY customer (id, first_name, last_name, birth, sex, tel_number, city, address, date) FROM stdin;
1	Pavol	GrofcĂ­k	1997-05-06 00:00:00	M	0917469415	ZĂˇkamennĂ©	MrzĂˇÄŤka 777	\N
\.


--
-- TOC entry 2860 (class 0 OID 82392)
-- Dependencies: 205
-- Data for Name: customer_event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY customer_event (id, event_id, cust_id, specification) FROM stdin;
\.


--
-- TOC entry 2858 (class 0 OID 82383)
-- Dependencies: 203
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY event (id, location, e_start, length, height, insurance) FROM stdin;
5	NovoĹĄ	2019-01-20 00:00:00	20	0.453000009	f
\.


--
-- TOC entry 2862 (class 0 OID 82411)
-- Dependencies: 207
-- Data for Name: specification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY specification (id, customer_event_id, duration, rank, length, finished) FROM stdin;
\.


--
-- TOC entry 2856 (class 0 OID 82369)
-- Dependencies: 201
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY transaction (id, account_id, t_time, event_id) FROM stdin;
\.


--
-- TOC entry 2876 (class 0 OID 0)
-- Dependencies: 198
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('account_id_seq', 3, true);


--
-- TOC entry 2877 (class 0 OID 0)
-- Dependencies: 204
-- Name: customer_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_event_id_seq', 1, false);


--
-- TOC entry 2878 (class 0 OID 0)
-- Dependencies: 196
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_id_seq', 1, true);


--
-- TOC entry 2879 (class 0 OID 0)
-- Dependencies: 202
-- Name: event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('event_id_seq', 5, true);


--
-- TOC entry 2880 (class 0 OID 0)
-- Dependencies: 206
-- Name: specification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('specification_id_seq', 1, false);


--
-- TOC entry 2881 (class 0 OID 0)
-- Dependencies: 200
-- Name: transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('transaction_id_seq', 1, false);


--
-- TOC entry 2716 (class 2606 OID 82360)
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- TOC entry 2722 (class 2606 OID 82397)
-- Name: customer_event customer_event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_event
    ADD CONSTRAINT customer_event_pkey PRIMARY KEY (id);


--
-- TOC entry 2714 (class 2606 OID 82349)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2720 (class 2606 OID 82388)
-- Name: event event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- TOC entry 2724 (class 2606 OID 82416)
-- Name: specification specification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY specification
    ADD CONSTRAINT specification_pkey PRIMARY KEY (id);


--
-- TOC entry 2718 (class 2606 OID 82374)
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 2726 (class 2606 OID 106663)
-- Name: transaction fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction
    ADD CONSTRAINT fkey FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE;


--
-- TOC entry 2725 (class 2606 OID 106668)
-- Name: account fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY account
    ADD CONSTRAINT fkey FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE;


--
-- TOC entry 2729 (class 2606 OID 106673)
-- Name: specification fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY specification
    ADD CONSTRAINT fkey FOREIGN KEY (customer_event_id) REFERENCES customer_event(id) ON DELETE CASCADE;


--
-- TOC entry 2727 (class 2606 OID 106678)
-- Name: customer_event fkey1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_event
    ADD CONSTRAINT fkey1 FOREIGN KEY (cust_id) REFERENCES customer(id) ON DELETE CASCADE;


--
-- TOC entry 2728 (class 2606 OID 106688)
-- Name: customer_event fkey2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_event
    ADD CONSTRAINT fkey2 FOREIGN KEY (event_id) REFERENCES event(id);


-- Completed on 2018-04-25 21:36:59

--
-- PostgreSQL database dump complete
--

