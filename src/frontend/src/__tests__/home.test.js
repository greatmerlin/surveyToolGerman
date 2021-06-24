import Home from "../Pages/Home.js";
import React from "react";
import { render } from "@testing-library/react";
import Title from "../Components/Title.js";
import { Button } from "react-bootstrap";
import { create } from "react-test-renderer";
import "@testing-library/jest-dom";

// tests that the <Home /> component renders without any problem (describe) 
describe("My first snapshot test", () => {
  test("testing app button", () => {
    let tree = create(<Home />);
    expect(tree.toJSON()).toMatchSnapshot();
  });
});

// test a custom Home Component, if it renders without any problem -> everything renders (test)
test("<Home /> matches snapshot", () => {
  const component = render(
    <>
      <Title>Online Umfragen</Title>
      <ul style={{ marginTop: "25vh" }}>
        {[
          "Erstelle einen Fragebogen mit einem benutzerfreundlichen und einfach verständlichen Umfragetool.",
          "Einfach verständliche Analyse von Umfrageergebnissen.",
          "Answerium, da oft Fragen auftauchen!",
        ].map((entry, i) => {
          return (
            <li className="my-3" key={i}>
              <p className="lead">{entry}</p>
            </li>
          );
        })}
      </ul>
      <Button href="/selectPath" variant="primary" className="my-3">
        Jetzt kostenlos starten!
      </Button>
    </>
  );

  expect(component.container).toMatchSnapshot();
});

// now let's test if the component renders without crashing
// we do the same thing as react, we pass the elements into a div and render it
// if a component doesn't render, then the test will fail and we know that our code crashes
test("<Home /> renders without crashing", () => {
  const div = document.createElement("div");
  render(
    <>
      <Title>Online Umfragen Mocjup</Title>
      <ul>
        {["one", "two"].map((entry, i) => {
          return (
            <li className="my-3" key={i}>
              <p className="lead">{entry}</p>
            </li>
          );
        })}
      </ul>
      <Button href="/selectPath" variant="primary" className="my-3">
        Jetzt kostenlos starten!
      </Button>
    </>,
    div
  );
});
