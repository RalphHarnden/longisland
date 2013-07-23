#!/bin/sh

#
# test the sample controller with default test user credentials
# (provided that a sample controller has been generated as part of the project)
#
curl http://localhost:9080/rest/hello?param0=Magnet -u'magnet:phTz' -k