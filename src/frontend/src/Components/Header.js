import React from "react";
import { Container } from "react-bootstrap";
import Logo from "./Logo";

/**
 * Header component.
 *
 * @returns JSX.Element
 */

export default function Header() {
  return (
    <Container>
      <header className="mt-5 mb-4">
        <a href="/" style={{ textDecoration: "none" }}>
          <h1 className="text-dark d-flex">
            <Logo />
            <span className="ml-3">Answerium</span>
          </h1>
        </a>
      </header>
    </Container>
  );
}
