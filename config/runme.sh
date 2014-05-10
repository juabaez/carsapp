#!/bin/bash

mysql -u root carsapp_test < config/schema.sql
mysql -u root carsapp_development < config/schema.sql
