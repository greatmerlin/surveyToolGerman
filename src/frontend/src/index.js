import React from "react";
import ReactDOM from "react-dom";
import Routing from "./Routing";
import Header from "./Components/Header";
import Footer from "./Components/Footer";
import { Container } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Style/Global.css";

/**
 * Renders layout.
 * Content of container is being determined by the client-side router.
 */

ReactDOM.render(
  <React.StrictMode>
    <Header />
    <Container>
      <Routing />
    </Container>
    <Footer />
  </React.StrictMode>,
  document.getElementById("root")
);
