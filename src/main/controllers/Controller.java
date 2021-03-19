package main.controllers;

import main.models.ObservableModel;
import main.views.View;

abstract class Controller {
    protected abstract void setModel(ObservableModel model);
    protected abstract void setView(View view);
}
