#!/usr/bin/env python
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
#
# Author: Maxim Stewart (itdominator@gmail.com)
# Copyright (C) 2017 Maxim Stewart
import gio, gtk, os, sys

def get_icon_filename(filename,size):
    #final_filename = "default_icon.png"
    final_filename = ""
    if os.path.isfile(filename):
        # Get the icon name
        file = gio.File(filename)
        file_info = file.query_info('standard::icon')
        file_icon = file_info.get_icon().get_names()[0]
        # Get the icon file path
        icon_theme = gtk.icon_theme_get_default()
        icon_filename = icon_theme.lookup_icon(file_icon, size, 0)
        if icon_filename != None:
            final_filename = icon_filename.get_filename()

    return final_filename
print get_icon_filename(sys.argv[1],64)
