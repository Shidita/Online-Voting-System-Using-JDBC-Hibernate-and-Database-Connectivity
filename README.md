# Online Voting System — Advanced Internet Programming

**Servlets + JSP (JSTL) + HttpSession + JDBC + Hibernate 6.** Runs on **Apache Tomcat 10.1 / 11** (Jakarta EE).
Database: embedded H2 — **no MySQL, no password, no SQL import needed.**

## Run in NetBeans (easiest)
1. Extract the zip. In NetBeans: **File → Open Project** → select the `voting-aip` folder.
2. Make sure your Tomcat 11 is added: **Services → Servers → Add Server → Apache Tomcat** (once only).
3. Right-click the project → **Properties → Run** → Server = Apache Tomcat 11.
4. Right-click the project → **Clean and Build** (first time needs internet to download libraries).
5. Right-click the project → **Run**.
6. Open **http://localhost:8080/voting-aip/** (use the port your Tomcat shows, e.g. 8080).

Needs **JDK 17 or newer** (Tomcat 11 needs it too).

## Run without an IDE
`mvn clean package` → copy `target/voting-aip.war` into Tomcat's `webapps` folder → start Tomcat →
open `http://localhost:8080/voting-aip/`

## Logins
- Admin: **admin / admin123** (add / delete candidates)
- Voter: click **Register**, create an account, then vote.
- Open the *Live results* page in one browser and vote from another: counts update every 3 seconds.

## Features
- Register / login / logout with `HttpSession` (session id regenerated at login, 30 min timeout)
- **One vote per user** — checked in code and enforced by a UNIQUE database constraint
- Live results (JavaScript `fetch` polls `/results-data` JSON every 3 seconds)
- Admin panel (role check in `AuthFilter`)
- Safe: PreparedStatement + HQL parameters, `<c:out>` escaping, PBKDF2 password hashing

## Architecture (MVC)
| Layer | Technology | Files |
|---|---|---|
| Model | Hibernate entities | `Candidate`, `Vote`, `ResultRow` |
| View | JSP + JSTL | `WEB-INF/views/*.jsp` |
| Controller | Servlets + Filter | `servlet/*.java` |
| Users | **JDBC** | `dao/UserDAO`, `util/DBUtil` |
| Candidates & votes | **Hibernate** | `dao/VoteDAO`, `util/HibernateUtil` |
| Access control | **HttpSession** + `AuthFilter` | `LoginServlet`, `AuthFilter` |
| Startup data | `ServletContextListener` | `AppInit` |

## Where is the data?
In `votingdb.mv.db` inside your user folder (e.g. `C:\Users\<you>\`). Delete that file to reset everything.

## Problems?
| Problem | Fix |
|---|---|
| Red marks in NetBeans | Right-click project → *Clean and Build with Dependencies*; check JDK 17+ |
| 500 NoClassDefFoundError (JSTL) | Right-click project → Clean and Build, then Run again (WAR must contain jstl jars) |
| 404 | Use the app name in the URL: `/voting-aip/` — and Tomcat must be 10.1 or 11 |
| "Database may be already in use" | Stop the old Tomcat run (only one copy may run) and run again |

## Viva flow
1. `POST /vote` → `AuthFilter` checks session → `VoteServlet` → `VoteDAO.castVote()` (Hibernate transaction).
2. Duplicate vote → blocked by `hasVoted`, and by `UNIQUE(voter)` if two requests race.
3. Results page → `fetch('/results-data')` → `ResultsDataServlet` → HQL `group by candidateId`.
