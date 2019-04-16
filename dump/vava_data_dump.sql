--
-- PostgreSQL database dump
--

-- Dumped from database version 10.2
-- Dumped by pg_dump version 10.2

-- Started on 2018-04-25 21:37:43

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

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


-- Completed on 2018-04-25 21:37:43

--
-- PostgreSQL database dump complete
--

