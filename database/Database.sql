--
-- PostgreSQL database dump
--

\restrict smdcKeK5lY15v9fpUhds2iUWQknJBB2sXspTsFrd1oRejLvLNLyiLEvXiFStQUd

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.6

-- Started on 2025-10-18 02:40:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 240 (class 1255 OID 17143)
-- Name: check_data_partecipazione(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_data_partecipazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF NEW.data_partecipazione >
     (SELECT data FROM sessione WHERE id_sessione = NEW.id_sessione) THEN
     	RAISE EXCEPTION 'errore: la data partecipazione è successiva alla data di svolgimento della sessione';
  END IF;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_data_partecipazione() OWNER TO postgres;

--
-- TOC entry 237 (class 1255 OID 16982)
-- Name: check_limite_sessioni(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_limite_sessioni() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.numero_sessione > (SELECT numero_sessioni FROM corso WHERE id_corso = NEW.id_corso) THEN
        RAISE EXCEPTION 'Il numero sessione supera il numero di sessioni previste dal corso';
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_limite_sessioni() OWNER TO postgres;

--
-- TOC entry 238 (class 1255 OID 16983)
-- Name: check_partecipazione_sessione_presenza(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_partecipazione_sessione_presenza() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NOT (SELECT in_presenza FROM sessione WHERE id_sessione = NEW.id_sessione) THEN
        RAISE EXCEPTION 'Impossibile registrare la partecipazione: la sessione non è una sessione in presenza.';
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_partecipazione_sessione_presenza() OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 16984)
-- Name: check_ricetta_sessione_presenza(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_ricetta_sessione_presenza() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NOT (SELECT in_presenza FROM sessione WHERE id_sessione = NEW.id_sessione) THEN
        RAISE EXCEPTION 'Errore: Le ricette possono essere associate solo a sessioni in presenza.';
    END IF;
	
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.check_ricetta_sessione_presenza() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16985)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id_categoria integer NOT NULL,
    descrizione character varying(50) NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16988)
-- Name: categoria_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_id_categoria_seq OWNER TO postgres;

--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 218
-- Name: categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;


--
-- TOC entry 219 (class 1259 OID 16989)
-- Name: chef; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chef (
    id_chef integer NOT NULL,
    nome character varying(50) NOT NULL,
    cognome character varying(50) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE public.chef OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16992)
-- Name: chef_id_chef_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chef_id_chef_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.chef_id_chef_seq OWNER TO postgres;

--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 220
-- Name: chef_id_chef_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chef_id_chef_seq OWNED BY public.chef.id_chef;


--
-- TOC entry 221 (class 1259 OID 16993)
-- Name: corso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.corso (
    id_corso integer NOT NULL,
    data_inizio date,
    numero_sessioni integer,
    id_frequenza integer,
    id_categoria integer,
    id_chef integer,
    CONSTRAINT corso_numero_sessioni_check CHECK (((numero_sessioni IS NULL) OR (numero_sessioni > 0)))
);


ALTER TABLE public.corso OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16997)
-- Name: corso_id_corso_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.corso_id_corso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.corso_id_corso_seq OWNER TO postgres;

--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 222
-- Name: corso_id_corso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.corso_id_corso_seq OWNED BY public.corso.id_corso;


--
-- TOC entry 223 (class 1259 OID 16998)
-- Name: frequenza_sessioni; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.frequenza_sessioni (
    id_frequenza integer NOT NULL,
    descrizione character varying(50) NOT NULL
);


ALTER TABLE public.frequenza_sessioni OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17001)
-- Name: frequenza_id_frequenza_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.frequenza_id_frequenza_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.frequenza_id_frequenza_seq OWNER TO postgres;

--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 224
-- Name: frequenza_id_frequenza_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.frequenza_id_frequenza_seq OWNED BY public.frequenza_sessioni.id_frequenza;


--
-- TOC entry 225 (class 1259 OID 17002)
-- Name: ingrediente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingrediente (
    id_ingrediente integer NOT NULL,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.ingrediente OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17005)
-- Name: ingrediente_id_ingrediente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingrediente_id_ingrediente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ingrediente_id_ingrediente_seq OWNER TO postgres;

--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 226
-- Name: ingrediente_id_ingrediente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingrediente_id_ingrediente_seq OWNED BY public.ingrediente.id_ingrediente;


--
-- TOC entry 227 (class 1259 OID 17006)
-- Name: ingredienti_utilizzati; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredienti_utilizzati (
    dose_grammi integer NOT NULL,
    id_ricetta integer NOT NULL,
    id_ingrediente integer NOT NULL,
    CONSTRAINT ingredienti_utilizzati_dose_grammi_check CHECK ((dose_grammi > 0))
);


ALTER TABLE public.ingredienti_utilizzati OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17010)
-- Name: iscrizione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.iscrizione (
    id_corso integer NOT NULL,
    id_utente integer NOT NULL
);


ALTER TABLE public.iscrizione OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17013)
-- Name: partecipazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partecipazione (
    data_partecipazione date NOT NULL,
    id_utente integer NOT NULL,
    id_sessione integer NOT NULL
);


ALTER TABLE public.partecipazione OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 17016)
-- Name: realizzazione_ricetta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realizzazione_ricetta (
    id_ricetta integer NOT NULL,
    id_sessione integer NOT NULL
);


ALTER TABLE public.realizzazione_ricetta OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17019)
-- Name: ricetta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ricetta (
    id_ricetta integer NOT NULL,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.ricetta OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 17022)
-- Name: ricetta_id_ricetta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ricetta_id_ricetta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ricetta_id_ricetta_seq OWNER TO postgres;

--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 232
-- Name: ricetta_id_ricetta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ricetta_id_ricetta_seq OWNED BY public.ricetta.id_ricetta;


--
-- TOC entry 233 (class 1259 OID 17023)
-- Name: sessione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessione (
    id_sessione integer NOT NULL,
    in_presenza boolean,
    data date NOT NULL,
    numero_sessione integer NOT NULL,
    url_meeting character varying(200),
    id_corso integer NOT NULL,
    CONSTRAINT check_url_presenza CHECK (((in_presenza = true) OR (url_meeting IS NOT NULL))),
    CONSTRAINT sessione_numero_sessione_check CHECK ((numero_sessione > 0))
);


ALTER TABLE public.sessione OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 17028)
-- Name: sessione_id_sessione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sessione_id_sessione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sessione_id_sessione_seq OWNER TO postgres;

--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 234
-- Name: sessione_id_sessione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sessione_id_sessione_seq OWNED BY public.sessione.id_sessione;


--
-- TOC entry 235 (class 1259 OID 17029)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    id_utente integer NOT NULL,
    nome character varying(50) NOT NULL,
    cognome character varying(50) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 17032)
-- Name: utente_id_utente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utente_id_utente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.utente_id_utente_seq OWNER TO postgres;

--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 236
-- Name: utente_id_utente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utente_id_utente_seq OWNED BY public.utente.id_utente;


--
-- TOC entry 4797 (class 2604 OID 17033)
-- Name: categoria id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);


--
-- TOC entry 4798 (class 2604 OID 17034)
-- Name: chef id_chef; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chef ALTER COLUMN id_chef SET DEFAULT nextval('public.chef_id_chef_seq'::regclass);


--
-- TOC entry 4799 (class 2604 OID 17035)
-- Name: corso id_corso; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso ALTER COLUMN id_corso SET DEFAULT nextval('public.corso_id_corso_seq'::regclass);


--
-- TOC entry 4800 (class 2604 OID 17036)
-- Name: frequenza_sessioni id_frequenza; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frequenza_sessioni ALTER COLUMN id_frequenza SET DEFAULT nextval('public.frequenza_id_frequenza_seq'::regclass);


--
-- TOC entry 4801 (class 2604 OID 17037)
-- Name: ingrediente id_ingrediente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingrediente ALTER COLUMN id_ingrediente SET DEFAULT nextval('public.ingrediente_id_ingrediente_seq'::regclass);


--
-- TOC entry 4802 (class 2604 OID 17038)
-- Name: ricetta id_ricetta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ricetta ALTER COLUMN id_ricetta SET DEFAULT nextval('public.ricetta_id_ricetta_seq'::regclass);


--
-- TOC entry 4803 (class 2604 OID 17039)
-- Name: sessione id_sessione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione ALTER COLUMN id_sessione SET DEFAULT nextval('public.sessione_id_sessione_seq'::regclass);


--
-- TOC entry 4804 (class 2604 OID 17040)
-- Name: utente id_utente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente ALTER COLUMN id_utente SET DEFAULT nextval('public.utente_id_utente_seq'::regclass);


--
-- TOC entry 5008 (class 0 OID 16985)
-- Dependencies: 217
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoria (id_categoria, descrizione) FROM stdin;
1	Cucina Italiana
2	Pasticceria
3	Cucina Asiatica
4	Panificazione
5	Cucina Vegetariana
6	Cucina Mediterranea
\.


--
-- TOC entry 5010 (class 0 OID 16989)
-- Dependencies: 219
-- Data for Name: chef; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chef (id_chef, nome, cognome, username, password) FROM stdin;
2	Carlo	Cracco	chefCracco	chefpasscracco
3	Gennaro	Esposito	chefEsposito	chefpassesposito
4	Bruno	Barbieri	chefBarbieri	chefpassbarbieri
5	Joe	Bastianich	chefBastianich	chefpassbastianich
1	Antonino	Cannavacciuolo	demo	demo
6	TEST	TEST	test	test
\.


--
-- TOC entry 5012 (class 0 OID 16993)
-- Dependencies: 221
-- Data for Name: corso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.corso (id_corso, data_inizio, numero_sessioni, id_frequenza, id_categoria, id_chef) FROM stdin;
1	2025-09-01	8	1	1	1
2	2025-09-03	6	2	2	2
3	2025-09-05	10	4	3	3
4	2025-09-10	4	3	4	4
5	2025-09-15	12	1	5	5
6	2025-09-20	6	2	6	1
7	2025-10-01	8	1	2	3
8	2025-10-05	5	4	4	2
10	2025-10-15	4	1	1	1
11	2025-10-15	4	1	2	1
12	2025-10-20	4	5	3	1
13	2025-10-13	4	2	4	1
14	2025-10-13	4	1	1	1
15	2025-10-13	4	2	1	1
16	2025-10-13	4	3	6	1
17	2025-10-13	4	4	1	1
18	2025-10-13	4	5	1	1
19	2025-10-15	8	1	1	6
20	2025-10-16	8	2	1	6
21	2025-10-16	6	3	3	6
22	2025-10-16	7	4	4	6
23	2025-10-16	16	5	6	6
24	2025-10-16	12	3	1	6
25	2025-10-16	4	3	4	6
\.


--
-- TOC entry 5014 (class 0 OID 16998)
-- Dependencies: 223
-- Data for Name: frequenza_sessioni; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.frequenza_sessioni (id_frequenza, descrizione) FROM stdin;
1	Settimanale
2	Ogni 2 settimane
3	Mensile
4	Ogni due giorni
5	Giornaliero
\.


--
-- TOC entry 5016 (class 0 OID 17002)
-- Dependencies: 225
-- Data for Name: ingrediente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingrediente (id_ingrediente, nome) FROM stdin;
1	Farina 00
2	Uova
3	Latte
4	Burro
5	Zucchero
6	Lievito di birra
7	Sale
8	Olio extravergine d'oliva
9	Pomodori pelati
10	Mozzarella
11	Basilico
12	Riso
13	Brodo vegetale
14	Parmigiano Reggiano
15	Cioccolato fondente
16	Panna fresca
17	Vaniglia
18	Mascarpone
19	Caffè
20	Salsa di soia
21	Cetrioli
22	Olive nere
23	Feta
24	Peperoni
25	Ceci
26	Tahini
27	Limone
28	Aglio
29	Curry in polvere
30	Latte di cocco
31	Patate
32	Carote
33	Noodles di riso
34	Germogli di soia
35	Arachidi
36	Salsa di pesce
37	Zenzero
38	Semi di sesamo
39	Salmone
40	Avocado
41	Alga nori
42	Rucola
43	Pomodorini
44	Aceto balsamico
45	Melanzane
46	Zucchine
47	Tofu
48	Funghi shiitake
49	Miele
50	Semi di papavero
51	Acqua
52	Guanciale
53	Pecorino Romano
54	Zafferano
55	Savoiardi
56	Cacao amaro
57	Aceto di riso
58	Pasta di tamarindo
59	Lime
60	Cipollotto
61	Miso
62	Kombu
63	Spaghetti
\.


--
-- TOC entry 5018 (class 0 OID 17006)
-- Dependencies: 227
-- Data for Name: ingredienti_utilizzati; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredienti_utilizzati (dose_grammi, id_ricetta, id_ingrediente) FROM stdin;
250	1	1
150	1	51
10	1	6
5	1	7
15	1	8
150	1	9
100	1	10
5	1	11
200	2	63
120	2	2
80	2	52
60	2	53
20	2	14
3	2	7
320	3	12
80	3	4
40	3	14
600	3	13
1	3	54
400	4	45
200	4	9
150	4	10
50	4	14
20	4	11
30	4	8
200	5	21
50	5	22
120	5	23
100	5	24
15	5	8
4	5	7
250	6	25
35	6	26
20	6	27
8	6	28
20	6	8
5	6	7
250	7	10
250	7	43
15	7	11
30	7	8
4	7	7
10	7	44
200	8	31
150	8	32
200	8	30
15	8	29
10	8	37
80	8	46
5	8	7
250	9	47
150	9	46
100	9	32
25	9	20
10	9	38
12	9	37
5	9	7
400	11	12
30	11	57
10	11	5
5	11	7
100	11	39
50	11	40
5	11	41
300	12	33
100	12	34
50	12	35
30	12	36
18	12	20
25	12	58
60	12	2
30	12	60
15	12	59
8	12	38
5	12	7
300	13	33
500	13	13
100	13	48
60	13	47
40	13	61
4	13	62
18	13	20
10	13	38
6	13	37
5	13	7
500	15	1
325	15	51
4	15	6
12	15	7
25	15	8
400	16	1
280	16	51
12	16	6
12	16	7
70	16	8
500	17	1
375	17	51
5	17	6
12	17	7
20	17	8
600	18	1
220	18	51
160	18	2
40	18	8
20	18	49
10	18	50
10	18	7
12	18	6
200	19	55
180	19	18
100	19	2
80	19	5
60	19	19
10	19	56
300	20	1
160	20	51
120	20	4
35	20	5
8	20	6
6	20	7
200	21	15
250	21	16
90	21	2
60	21	5
3	21	17
300	22	1
150	22	4
120	22	2
120	22	5
2	22	17
2	22	7
\.


--
-- TOC entry 5019 (class 0 OID 17010)
-- Dependencies: 228
-- Data for Name: iscrizione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.iscrizione (id_corso, id_utente) FROM stdin;
1	1
1	3
1	4
1	5
1	6
1	8
1	10
2	2
2	4
2	7
2	9
3	1
3	2
3	3
3	6
3	7
3	10
4	2
4	4
4	6
4	8
4	10
5	1
5	2
5	3
5	5
5	6
5	7
5	8
5	9
6	3
6	5
6	10
7	1
7	2
7	4
7	5
7	7
7	9
8	2
8	3
8	6
8	8
\.


--
-- TOC entry 5020 (class 0 OID 17013)
-- Dependencies: 229
-- Data for Name: partecipazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.partecipazione (data_partecipazione, id_utente, id_sessione) FROM stdin;
2025-09-08	1	2
2025-09-08	3	2
2025-09-08	5	2
2025-09-08	6	2
2025-09-08	10	2
2025-09-22	1	4
2025-09-22	3	4
2025-09-22	4	4
2025-09-22	8	4
2025-10-06	1	6
2025-10-06	4	6
2025-10-06	5	6
2025-10-06	8	6
2025-10-06	10	6
2025-10-20	3	8
2025-10-20	6	8
2025-10-20	8	8
2025-09-17	2	10
2025-09-17	4	10
2025-09-17	9	10
2025-10-15	4	12
2025-10-15	7	12
2025-11-12	2	14
2025-11-12	4	14
2025-11-12	9	14
2025-09-07	2	16
2025-09-07	3	16
2025-09-07	7	16
2025-09-07	10	16
2025-09-11	1	18
2025-09-11	6	18
2025-09-11	10	18
2025-09-15	1	20
2025-09-15	2	20
2025-09-15	3	20
2025-09-19	2	22
2025-09-19	7	22
2025-09-19	10	22
2025-09-23	1	24
2025-09-23	6	24
2025-09-23	10	24
2025-10-10	2	26
2025-10-10	4	26
2025-10-10	6	26
2025-10-10	10	26
2025-12-10	4	28
2025-12-10	8	28
2025-12-10	10	28
2025-09-22	3	30
2025-09-22	5	30
2025-09-22	6	30
2025-09-22	7	30
2025-09-22	8	30
2025-09-22	9	30
2025-10-06	2	32
2025-10-06	3	32
2025-10-06	8	32
2025-10-20	1	34
2025-10-20	5	34
2025-10-20	7	34
2025-11-03	2	36
2025-11-03	6	36
2025-11-03	9	36
2025-11-17	1	38
2025-11-17	3	38
2025-11-17	5	38
2025-11-17	7	38
2025-12-01	2	40
2025-12-01	8	40
2025-12-01	9	40
2025-10-04	3	42
2025-10-04	5	42
2025-10-04	10	42
2025-11-01	3	44
2025-11-01	10	44
2025-11-29	5	46
2025-10-08	1	48
2025-10-08	2	48
2025-10-08	5	48
2025-10-08	9	48
2025-10-22	4	50
2025-10-22	7	50
2025-11-05	1	52
2025-11-05	4	52
2025-11-05	9	52
2025-11-19	2	54
2025-11-19	5	54
2025-11-19	7	54
2025-10-07	3	56
2025-10-07	6	56
2025-10-07	8	56
2025-10-11	2	58
2025-10-11	3	58
\.


--
-- TOC entry 5021 (class 0 OID 17016)
-- Dependencies: 230
-- Data for Name: realizzazione_ricetta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realizzazione_ricetta (id_ricetta, id_sessione) FROM stdin;
1	2
2	2
3	4
4	6
1	8
3	8
19	10
20	10
21	12
22	14
11	16
12	18
13	18
8	20
11	22
12	24
15	26
16	26
17	28
18	28
8	30
9	32
6	32
8	34
9	36
6	38
8	40
9	40
5	42
6	42
7	44
19	48
22	48
20	50
21	52
22	52
19	54
20	54
15	56
17	56
16	58
18	58
1	4
13	4
4	46
4	62
6	62
12	62
2	61
13	61
11	66
11	64
1	92
2	92
2	93
7	97
8	148
2	148
1	148
2	138
3	138
\.


--
-- TOC entry 5022 (class 0 OID 17019)
-- Dependencies: 231
-- Data for Name: ricetta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ricetta (id_ricetta, nome) FROM stdin;
1	Pizza Margherita
2	Pasta alla carbonara
3	Risotto allo zafferano
4	Parmigiana di Melanzane
5	Insalata Greca
6	Hummus
7	Insalata Caprese
8	Curry di Verdure
9	Tofu Saltato con Verdure
11	Sushi
12	Pad Thai
13	Ramen Vegetariano
15	Pane casereccio
16	Focaccia genovese
17	Ciabatta
18	Challah
19	Tiramisù
20	Croissant
21	Mousse al cioccolato
22	Torta della nonna
\.


--
-- TOC entry 5024 (class 0 OID 17023)
-- Dependencies: 233
-- Data for Name: sessione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessione (id_sessione, in_presenza, data, numero_sessione, url_meeting, id_corso) FROM stdin;
1	f	2025-09-01	1	https://meeting.example.com/corso1-sessione1	1
2	t	2025-09-08	2	\N	1
3	f	2025-09-15	3	https://meeting.example.com/corso1-sessione3	1
4	t	2025-09-22	4	\N	1
5	f	2025-09-29	5	https://meeting.example.com/corso1-sessione5	1
6	t	2025-10-06	6	\N	1
7	f	2025-10-13	7	https://meeting.example.com/corso1-sessione7	1
8	t	2025-10-20	8	\N	1
9	f	2025-09-03	1	https://meeting.example.com/corso2-sessione1	2
10	t	2025-09-17	2	\N	2
11	f	2025-10-01	3	https://meeting.example.com/corso2-sessione3	2
12	t	2025-10-15	4	\N	2
13	f	2025-10-29	5	https://meeting.example.com/corso2-sessione5	2
14	t	2025-11-12	6	\N	2
15	f	2025-09-05	1	https://meeting.example.com/corso3-sessione1	3
16	t	2025-09-07	2	\N	3
17	f	2025-09-09	3	https://meeting.example.com/corso3-sessione3	3
18	t	2025-09-11	4	\N	3
19	f	2025-09-13	5	https://meeting.example.com/corso3-sessione5	3
20	t	2025-09-15	6	\N	3
21	f	2025-09-17	7	https://meeting.example.com/corso3-sessione7	3
22	t	2025-09-19	8	\N	3
23	f	2025-09-21	9	https://meeting.example.com/corso3-sessione9	3
24	t	2025-09-23	10	\N	3
25	f	2025-09-10	1	https://meeting.example.com/corso4-sessione1	4
26	t	2025-10-10	2	\N	4
27	f	2025-11-10	3	https://meeting.example.com/corso4-sessione3	4
28	t	2025-12-10	4	\N	4
29	f	2025-09-15	1	https://meeting.example.com/corso5-sessione1	5
30	t	2025-09-22	2	\N	5
31	f	2025-09-29	3	https://meeting.example.com/corso5-sessione3	5
32	t	2025-10-06	4	\N	5
33	f	2025-10-13	5	https://meeting.example.com/corso5-sessione5	5
34	t	2025-10-20	6	\N	5
35	f	2025-10-27	7	https://meeting.example.com/corso5-sessione7	5
36	t	2025-11-03	8	\N	5
37	f	2025-11-10	9	https://meeting.example.com/corso5-sessione9	5
38	t	2025-11-17	10	\N	5
39	f	2025-11-24	11	https://meeting.example.com/corso5-sessione11	5
40	t	2025-12-01	12	\N	5
41	f	2025-09-20	1	https://meeting.example.com/corso6-sessione1	6
42	t	2025-10-04	2	\N	6
43	f	2025-10-18	3	https://meeting.example.com/corso6-sessione3	6
44	t	2025-11-01	4	\N	6
45	f	2025-11-15	5	https://meeting.example.com/corso6-sessione5	6
46	t	2025-11-29	6	\N	6
47	f	2025-10-01	1	https://meeting.example.com/corso7-sessione1	7
48	t	2025-10-08	2	\N	7
49	f	2025-10-15	3	https://meeting.example.com/corso7-sessione3	7
50	t	2025-10-22	4	\N	7
51	f	2025-10-29	5	https://meeting.example.com/corso7-sessione5	7
52	t	2025-11-05	6	\N	7
53	f	2025-11-12	7	https://meeting.example.com/corso7-sessione7	7
54	t	2025-11-19	8	\N	7
55	f	2025-10-05	1	https://meeting.example.com/corso8-sessione1	8
56	t	2025-10-07	2	\N	8
57	f	2025-10-09	3	https://meeting.example.com/corso8-sessione3	8
58	t	2025-10-11	4	\N	8
59	f	2025-10-13	5	https://meeting.example.com/corso8-sessione5	8
60	f	2025-10-15	1	https://meet.uninafoodlab.it/11/1	11
61	t	2025-10-22	2	\N	11
62	t	2025-10-29	3	\N	11
63	f	2025-11-05	4	https://meet.uninafoodlab.it/11/4	11
64	t	2025-10-20	1	\N	12
65	f	2025-10-27	2	https://meet.uninafoodlab/demo.com/12/2	12
66	t	2025-11-03	3	\N	12
67	f	2025-11-10	4	https://meet.uninafoodlab/demo.com/12/4	12
68	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/13/1	13
69	f	2025-10-20	2	https://meet.uninafoodlab/demo.com/13/2	13
70	f	2025-10-27	3	https://meet.uninafoodlab/demo.com/13/3	13
71	f	2025-11-03	4	https://meet.uninafoodlab/demo.com/13/4	13
72	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/14/1	14
73	f	2025-10-20	2	https://meet.uninafoodlab/demo.com/14/2	14
74	f	2025-10-27	3	https://meet.uninafoodlab/demo.com/14/3	14
75	f	2025-11-03	4	https://meet.uninafoodlab/demo.com/14/4	14
76	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/15/1	15
77	f	2025-10-27	2	https://meet.uninafoodlab/demo.com/15/2	15
78	f	2025-11-10	3	https://meet.uninafoodlab/demo.com/15/3	15
79	f	2025-11-24	4	https://meet.uninafoodlab/demo.com/15/4	15
80	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/16/1	16
81	f	2025-11-12	2	https://meet.uninafoodlab/demo.com/16/2	16
82	f	2025-12-12	3	https://meet.uninafoodlab/demo.com/16/3	16
83	f	2026-01-11	4	https://meet.uninafoodlab/demo.com/16/4	16
84	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/17/1	17
85	f	2025-10-15	2	https://meet.uninafoodlab/demo.com/17/2	17
86	f	2025-10-17	3	https://meet.uninafoodlab/demo.com/17/3	17
87	f	2025-10-19	4	https://meet.uninafoodlab/demo.com/17/4	17
88	f	2025-10-13	1	https://meet.uninafoodlab/demo.com/18/1	18
89	f	2025-10-14	2	https://meet.uninafoodlab/demo.com/18/2	18
90	f	2025-10-15	3	https://meet.uninafoodlab/demo.com/18/3	18
91	f	2025-10-16	4	https://meet.uninafoodlab/demo.com/18/4	18
92	t	2025-10-15	1	\N	19
93	t	2025-10-22	2	\N	19
94	f	2025-10-29	3	https://meet.uninafoodlab/demo.com/19/3	19
95	f	2025-11-05	4	https://meet.uninafoodlab/demo.com/19/4	19
96	f	2025-11-12	5	https://meet.uninafoodlab/demo.com/19/5	19
97	t	2025-11-19	6	\N	19
98	f	2025-11-26	7	https://meet.uninafoodlab/demo.com/19/7	19
99	f	2025-12-03	8	https://meet.uninafoodlab/demo.com/19/8	19
100	f	2025-10-16	1	https://meet.uninafoodlab/demo.com/20/1	20
101	t	2025-10-30	2	\N	20
102	t	2025-11-13	3	\N	20
103	f	2025-11-27	4	https://meet.uninafoodlab/demo.com/20/4	20
104	f	2025-12-11	5	https://meet.uninafoodlab/demo.com/20/5	20
105	f	2025-12-25	6	https://meet.uninafoodlab/demo.com/20/6	20
106	t	2026-01-08	7	\N	20
107	f	2026-01-22	8	https://meet.uninafoodlab/demo.com/20/8	20
108	f	2025-10-16	1	https://meet.uninafoodlab/demo.com/21/1	21
109	f	2025-11-15	2	https://meet.uninafoodlab/demo.com/21/2	21
110	f	2025-12-15	3	https://meet.uninafoodlab/demo.com/21/3	21
111	f	2026-01-14	4	https://meet.uninafoodlab/demo.com/21/4	21
112	f	2026-02-13	5	https://meet.uninafoodlab/demo.com/21/5	21
113	t	2026-03-15	6	\N	21
114	f	2025-10-16	1	https://meet.uninafoodlab/demo.com/22/1	22
115	f	2025-10-18	2	https://meet.uninafoodlab/demo.com/22/2	22
116	t	2025-10-20	3	\N	22
117	t	2025-10-22	4	\N	22
118	t	2025-10-24	5	\N	22
119	f	2025-10-26	6	https://meet.uninafoodlab/demo.com/22/6	22
120	f	2025-10-28	7	https://meet.uninafoodlab/demo.com/22/7	22
121	f	2025-10-16	1	https://meet.uninafoodlab/demo.com/23/1	23
122	t	2025-10-17	2	\N	23
123	t	2025-10-18	3	\N	23
124	f	2025-10-19	4	https://meet.uninafoodlab/demo.com/23/4	23
125	f	2025-10-20	5	https://meet.uninafoodlab/demo.com/23/5	23
126	f	2025-10-21	6	https://meet.uninafoodlab/demo.com/23/6	23
127	f	2025-10-22	7	https://meet.uninafoodlab/demo.com/23/7	23
128	f	2025-10-23	8	https://meet.uninafoodlab/demo.com/23/8	23
129	f	2025-10-24	9	https://meet.uninafoodlab/demo.com/23/9	23
130	t	2025-10-25	10	\N	23
131	t	2025-10-26	11	\N	23
132	f	2025-10-27	12	https://meet.uninafoodlab/demo.com/23/12	23
133	f	2025-10-28	13	https://meet.uninafoodlab/demo.com/23/13	23
134	f	2025-10-29	14	https://meet.uninafoodlab/demo.com/23/14	23
135	t	2025-10-30	15	\N	23
136	t	2025-10-31	16	\N	23
137	t	2025-10-16	1	\N	24
138	t	2025-11-15	2	\N	24
139	f	2025-12-15	3	https://meet.uninafoodlab/demo.com/24/3	24
140	f	2026-01-14	4	https://meet.uninafoodlab/demo.com/24/4	24
141	f	2026-02-13	5	https://meet.uninafoodlab/demo.com/24/5	24
142	f	2026-03-15	6	https://meet.uninafoodlab/demo.com/24/6	24
143	f	2026-04-14	7	https://meet.uninafoodlab/demo.com/24/7	24
144	f	2026-05-14	8	https://meet.uninafoodlab/demo.com/24/8	24
145	f	2026-06-13	9	https://meet.uninafoodlab/demo.com/24/9	24
146	f	2026-07-13	10	https://meet.uninafoodlab/demo.com/24/10	24
147	f	2026-08-12	11	https://meet.uninafoodlab/demo.com/24/11	24
148	t	2026-09-11	12	\N	24
149	t	2025-10-16	1	\N	25
150	t	2025-11-15	2	\N	25
151	t	2025-12-15	3	\N	25
152	f	2026-01-14	4	https://meet.uninafoodlab/demo.com/25/4	25
\.


--
-- TOC entry 5026 (class 0 OID 17029)
-- Dependencies: 235
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (id_utente, nome, cognome, username, password) FROM stdin;
1	Antonio	Esposito	antonio_e	userpassesposito
2	Antonio	Pedata	antonio_p	userpasspedata
3	Pietro	Farina	pietro_f	userpassfarina
4	Raffaele	Fortini	raffaele_f	userpassfortini
5	Claudia	Sodorino	claudia_s	userpasssodorino
6	Miryam	Schetter	miryam_s	userpassschetter
7	Raffaella	Merenda	raffaella_m	userpassmerenda
8	Nunzia	Agrillo	nunzia_a	userpassagrillo
9	Andrea	Urciuoli	andrea_u	userpassurciuoli
10	Emanuele	De Rosa	emanuele_d	userpassderosa
\.


--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 218
-- Name: categoria_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_id_categoria_seq', 1, false);


--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 220
-- Name: chef_id_chef_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chef_id_chef_seq', 2, true);


--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 222
-- Name: corso_id_corso_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.corso_id_corso_seq', 25, true);


--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 224
-- Name: frequenza_id_frequenza_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.frequenza_id_frequenza_seq', 1, false);


--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 226
-- Name: ingrediente_id_ingrediente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingrediente_id_ingrediente_seq', 1, false);


--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 232
-- Name: ricetta_id_ricetta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ricetta_id_ricetta_seq', 1, false);


--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 234
-- Name: sessione_id_sessione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sessione_id_sessione_seq', 152, true);


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 236
-- Name: utente_id_utente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_id_utente_seq', 1, false);


--
-- TOC entry 4810 (class 2606 OID 17042)
-- Name: categoria categoria_descrizione_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_descrizione_key UNIQUE (descrizione);


--
-- TOC entry 4812 (class 2606 OID 17044)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria);


--
-- TOC entry 4814 (class 2606 OID 17046)
-- Name: chef chef_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chef
    ADD CONSTRAINT chef_pkey PRIMARY KEY (id_chef);


--
-- TOC entry 4816 (class 2606 OID 17048)
-- Name: chef chef_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chef
    ADD CONSTRAINT chef_username_key UNIQUE (username);


--
-- TOC entry 4818 (class 2606 OID 17050)
-- Name: corso corso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso
    ADD CONSTRAINT corso_pkey PRIMARY KEY (id_corso);


--
-- TOC entry 4820 (class 2606 OID 17052)
-- Name: frequenza_sessioni frequenza_descrizione_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frequenza_sessioni
    ADD CONSTRAINT frequenza_descrizione_key UNIQUE (descrizione);


--
-- TOC entry 4822 (class 2606 OID 17054)
-- Name: frequenza_sessioni frequenza_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.frequenza_sessioni
    ADD CONSTRAINT frequenza_pkey PRIMARY KEY (id_frequenza);


--
-- TOC entry 4824 (class 2606 OID 17056)
-- Name: ingrediente ingrediente_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingrediente
    ADD CONSTRAINT ingrediente_nome_key UNIQUE (nome);


--
-- TOC entry 4826 (class 2606 OID 17058)
-- Name: ingrediente ingrediente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingrediente
    ADD CONSTRAINT ingrediente_pkey PRIMARY KEY (id_ingrediente);


--
-- TOC entry 4828 (class 2606 OID 17060)
-- Name: ingredienti_utilizzati ingredienti_utilizzati_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredienti_utilizzati
    ADD CONSTRAINT ingredienti_utilizzati_pkey PRIMARY KEY (id_ricetta, id_ingrediente);


--
-- TOC entry 4830 (class 2606 OID 17062)
-- Name: iscrizione iscrizione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_pkey PRIMARY KEY (id_corso, id_utente);


--
-- TOC entry 4832 (class 2606 OID 17064)
-- Name: partecipazione partecipazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partecipazione
    ADD CONSTRAINT partecipazione_pkey PRIMARY KEY (id_utente, id_sessione);


--
-- TOC entry 4834 (class 2606 OID 17066)
-- Name: realizzazione_ricetta realizzazione_ricetta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realizzazione_ricetta
    ADD CONSTRAINT realizzazione_ricetta_pkey PRIMARY KEY (id_ricetta, id_sessione);


--
-- TOC entry 4836 (class 2606 OID 17068)
-- Name: ricetta ricetta_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ricetta
    ADD CONSTRAINT ricetta_nome_key UNIQUE (nome);


--
-- TOC entry 4838 (class 2606 OID 17070)
-- Name: ricetta ricetta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ricetta
    ADD CONSTRAINT ricetta_pkey PRIMARY KEY (id_ricetta);


--
-- TOC entry 4840 (class 2606 OID 17072)
-- Name: sessione sessione_id_corso_numero_sessione_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_id_corso_numero_sessione_key UNIQUE (id_corso, numero_sessione);


--
-- TOC entry 4842 (class 2606 OID 17074)
-- Name: sessione sessione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_pkey PRIMARY KEY (id_sessione);


--
-- TOC entry 4844 (class 2606 OID 17076)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id_utente);


--
-- TOC entry 4846 (class 2606 OID 17078)
-- Name: utente utente_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_username_key UNIQUE (username);


--
-- TOC entry 4859 (class 2620 OID 17144)
-- Name: partecipazione ensure_data_partecipazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER ensure_data_partecipazione BEFORE INSERT OR UPDATE ON public.partecipazione FOR EACH ROW EXECUTE FUNCTION public.check_data_partecipazione();


--
-- TOC entry 4860 (class 2620 OID 17079)
-- Name: partecipazione ensure_partecipazione_sessione_presenza; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER ensure_partecipazione_sessione_presenza BEFORE INSERT ON public.partecipazione FOR EACH ROW EXECUTE FUNCTION public.check_partecipazione_sessione_presenza();


--
-- TOC entry 4861 (class 2620 OID 17080)
-- Name: realizzazione_ricetta ensure_ricetta_sessione_presenza; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER ensure_ricetta_sessione_presenza BEFORE INSERT ON public.realizzazione_ricetta FOR EACH ROW EXECUTE FUNCTION public.check_ricetta_sessione_presenza();


--
-- TOC entry 4862 (class 2620 OID 17081)
-- Name: sessione trigger_check_limite_sessioni; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trigger_check_limite_sessioni BEFORE INSERT OR UPDATE ON public.sessione FOR EACH ROW EXECUTE FUNCTION public.check_limite_sessioni();


--
-- TOC entry 4847 (class 2606 OID 17082)
-- Name: corso corso_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso
    ADD CONSTRAINT corso_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id_categoria);


--
-- TOC entry 4848 (class 2606 OID 17087)
-- Name: corso corso_id_chef_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso
    ADD CONSTRAINT corso_id_chef_fkey FOREIGN KEY (id_chef) REFERENCES public.chef(id_chef) ON DELETE CASCADE;


--
-- TOC entry 4849 (class 2606 OID 17092)
-- Name: corso corso_id_frequenza_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso
    ADD CONSTRAINT corso_id_frequenza_fkey FOREIGN KEY (id_frequenza) REFERENCES public.frequenza_sessioni(id_frequenza);


--
-- TOC entry 4850 (class 2606 OID 17097)
-- Name: ingredienti_utilizzati ingredienti_utilizzati_id_ingrediente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredienti_utilizzati
    ADD CONSTRAINT ingredienti_utilizzati_id_ingrediente_fkey FOREIGN KEY (id_ingrediente) REFERENCES public.ingrediente(id_ingrediente) ON DELETE CASCADE;


--
-- TOC entry 4851 (class 2606 OID 17102)
-- Name: ingredienti_utilizzati ingredienti_utilizzati_id_ricetta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredienti_utilizzati
    ADD CONSTRAINT ingredienti_utilizzati_id_ricetta_fkey FOREIGN KEY (id_ricetta) REFERENCES public.ricetta(id_ricetta) ON DELETE CASCADE;


--
-- TOC entry 4852 (class 2606 OID 17107)
-- Name: iscrizione iscrizione_id_corso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_id_corso_fkey FOREIGN KEY (id_corso) REFERENCES public.corso(id_corso) ON DELETE CASCADE;


--
-- TOC entry 4853 (class 2606 OID 17112)
-- Name: iscrizione iscrizione_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON DELETE CASCADE;


--
-- TOC entry 4854 (class 2606 OID 17117)
-- Name: partecipazione partecipazione_id_sessione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partecipazione
    ADD CONSTRAINT partecipazione_id_sessione_fkey FOREIGN KEY (id_sessione) REFERENCES public.sessione(id_sessione) ON DELETE CASCADE;


--
-- TOC entry 4855 (class 2606 OID 17122)
-- Name: partecipazione partecipazione_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partecipazione
    ADD CONSTRAINT partecipazione_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON DELETE CASCADE;


--
-- TOC entry 4856 (class 2606 OID 17127)
-- Name: realizzazione_ricetta realizzazione_ricetta_id_ricetta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realizzazione_ricetta
    ADD CONSTRAINT realizzazione_ricetta_id_ricetta_fkey FOREIGN KEY (id_ricetta) REFERENCES public.ricetta(id_ricetta) ON DELETE CASCADE;


--
-- TOC entry 4857 (class 2606 OID 17132)
-- Name: realizzazione_ricetta realizzazione_ricetta_id_sessione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realizzazione_ricetta
    ADD CONSTRAINT realizzazione_ricetta_id_sessione_fkey FOREIGN KEY (id_sessione) REFERENCES public.sessione(id_sessione) ON DELETE CASCADE;


--
-- TOC entry 4858 (class 2606 OID 17137)
-- Name: sessione sessione_id_corso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_id_corso_fkey FOREIGN KEY (id_corso) REFERENCES public.corso(id_corso);


-- Completed on 2025-10-18 02:40:02

--
-- PostgreSQL database dump complete
--

\unrestrict smdcKeK5lY15v9fpUhds2iUWQknJBB2sXspTsFrd1oRejLvLNLyiLEvXiFStQUd

