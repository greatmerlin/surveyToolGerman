import React from "react";
import Home from "./Pages/Home";
import SelectPath from "./Pages/SelectPath";
import Create from "./Pages/Create";
import Participate from "./Pages/Participate";
import Evaluate from "./Pages/Evaluate";
import FAQ from "./Pages/FAQ";
import NotFound from "./Pages/NotFound";
import { BrowserRouter, Switch, Route } from "react-router-dom";

/**
 * Client-side routing
 *
 * @returns JSX.Element
 */

export default function Routing() {
  return (
    <>
      <BrowserRouter>
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/selectPath" exact component={SelectPath} />
          <Route path="/create" exact component={Create} />
          <Route path="/participate/:id" component={Participate} />
          <Route path="/participate/" component={Participate} />
          <Route path="/evaluate/:id" component={Evaluate} />
          <Route path="/evaluate/" component={Evaluate} />
          <Route path="/faq/" component={FAQ} />
          <Route path="*" component={NotFound} status={404} />
        </Switch>
      </BrowserRouter>
    </>
  );
}
