package main.controllers;

import main.models.ObservableModel;
import main.views.View;

interface Controller {
    void setModel(ObservableModel model);
    void setView(View view);
}
