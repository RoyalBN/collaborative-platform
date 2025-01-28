import { Routes } from '@angular/router';
import {  AppComponent } from "./app.component";
import {  AboutComponent } from "./features/about/about.component";
import {  DashboardComponent } from "./features/dashboard/dashboard.component";
import {  LoginComponent } from "./features/login/login.component";
import {  RegisterComponent } from "./features/register/register.component";
import {  ProfileComponent } from "./features/profile/profile.component";
import {  EditComponent } from "./features/edit/edit.component";
import {  ItemsListComponent } from "./features/items-list/items-list.component";
import {  ItemDetailsComponent } from "./features/item-details/item-details.component";
import {  NotFoundComponent } from "./features/not-found/not-found.component";
import {  UsersComponent } from "./features/admin/users/users.component";
import {  SettingsComponent } from "./features/admin/settings/settings.component";

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'edit/:id', component: EditComponent },
  { path: 'items', component: ItemsListComponent },
  { path: 'items/:id', component: ItemDetailsComponent },
  { path: 'admin', component: ItemDetailsComponent,
    children: [
      { path: 'settings', component: SettingsComponent },
      { path: 'users', component: UsersComponent },
    ]
  },
  { path: 'about', component: AboutComponent },
  { path: '**', component: NotFoundComponent }
];