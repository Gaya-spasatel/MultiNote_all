# Для начала указываем исходный образ, он будет использован как основа
FROM php:5.6-apache
MAINTAINER Daria Peregudova <dashapereg@mai.ru>

# RUN выполняет идущую за ней команду в контексте нашего образа.
# В данном случае мы установим некоторые зависимости и модули PHP.
# Для установки модулей используем команду docker-php-ext-install.
# На каждый RUN создается новый слой в образе, поэтому рекомендуется объединять команды.
#RUN groupadd -r mysql && useradd -r -g mysql mysql
RUN apt-get update && apt-get install -y \
        curl \
        wget \
        git \
        mc \
        apt-utils \
        bash \
        vim \
    && docker-php-ext-install -j$(nproc) mysqli pdo_mysql

# add gosu for easy step-down from root
# https://github.com/tianon/gosu/releases
#ENV GOSU_VERSION 1.12
#RUN set -eux; \
#	savedAptMark="$(apt-mark showmanual)"; \
#	apt-get update; \
#	apt-get install -y --no-install-recommends ca-certificates wget; \
#	rm -rf /var/lib/apt/lists/*; \
#	dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')"; \
#	wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch"; \
#	wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc"; \
#	export GNUPGHOME="$(mktemp -d)"; \
#	gpg --batch --keyserver hkps://keys.openpgp.org --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4; \
#	gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu; \
#	gpgconf --kill all; \
#	rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc; \
#	apt-mark auto '.*' > /dev/null; \
#	[ -z "$savedAptMark" ] || apt-mark manual $savedAptMark > /dev/null; \
#	apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false; \
#	chmod +x /usr/local/bin/gosu; \
#	gosu --version; \
#	gosu nobody true

#RUN mkdir /docker-entrypoint-initdb.d

#RUN apt-get update && apt-get install -y --no-install-recommends \
#		perl \
#		xz-utils \
#                dirmngr \
#	&& rm -rf /var/lib/apt/lists/*    

#RUN set -ex; \
# gpg: key 5072E1F5: public key "MySQL Release Engineering <mysql-build@oss.oracle.com>" imported
#	key='A4A9406876FCBD3C456770C88C718D3B5072E1F5'; \
#	export GNUPGHOME="$(mktemp -d)"; \
#	gpg --batch --keyserver ha.pool.sks-keyservers.net --recv-keys "$key"; \
#	gpg --batch --export "$key" > /etc/apt/trusted.gpg.d/mysql.gpg; \
#	gpgconf --kill all; \
#	rm -rf "$GNUPGHOME"; \
#	apt-key list > /dev/null

#ENV MYSQL_MAJOR 5.6
#ENV MYSQL_VERSION 5.6.51-1debian9

#RUN echo 'deb http://repo.mysql.com/apt/debian/ stretch mysql-5.6' > /etc/apt/sources.list.d/mysql.list

# the "/var/lib/mysql" stuff here is because the mysql-server postinst doesn't have an explicit way to disable the mysql_install_db codepath besides having a database already "configured" (ie, stuff in /var/lib/mysql/mysql)
# also, we set debconf keys to make APT a little quieter
#RUN { \
#		echo mysql-community-server mysql-community-server/data-dir select ''; \
#		echo mysql-community-server mysql-community-server/root-pass password ''; \
#		echo mysql-community-server mysql-community-server/re-root-pass password ''; \
#		echo mysql-community-server mysql-community-server/remove-test-db select false; \
#	} | debconf-set-selections \
#	&& apt-get update \
#	&& apt-get install -y \
#		mysql-server="${MYSQL_VERSION}" \
## comment out a few problematic configuration values
#	&& find /etc/mysql/ -name '*.cnf' -print0 \
#		| xargs -0 grep -lZE '^(bind-address|log)' \
#		| xargs -rt -0 sed -Ei 's/^(bind-address|log)/#&/' \
## don't reverse lookup hostnames, they are usually another container
#	&& echo '[mysqld]\nskip-host-cache\nskip-name-resolve' > /etc/mysql/conf.d/docker.cnf \
#	&& rm -rf /var/lib/apt/lists/* \
#	&& rm -rf /var/lib/mysql && mkdir -p /var/lib/mysql /var/run/mysqld \
#	&& chown -R mysql:mysql /var/lib/mysql /var/run/mysqld \
## ensure that /var/run/mysqld (used for socket and lock files) is writable regardless of the UID our mysqld instance ends up having at runtime
#	&& chmod 1777 /var/run/mysqld /var/lib/mysql

#VOLUME /var/lib/mysql

# Куда же без composer'а.
#RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

#COPY docker-entrypoint.sh /usr/local/bin/
#RUN ln -s usr/local/bin/docker-entrypoint.sh /entrypoint.sh # backwards compat
#COPY *.sql /docker-entrypoint-initdb.d/
##RUN /usr/local/bin/docker-entrypoint.sh mysqld
#ENTRYPOINT ["docker-entrypoint.sh"]

RUN mkdir /var/www/html/notes
RUN mkdir /var/www/html/notes/app
RUN mkdir /var/www/html/notes/login
RUN chown -R www-data:www-data /var/www/html/notes
RUN chmod 1775 /var/www/html/notes
RUN chmod 1775 /var/www/html/notes/app
RUN chmod 1775 /var/www/html/notes/login
ADD --chown=www-data:www-data notes/app/* /var/www/html/notes/app/
ADD --chown=www-data:www-data notes/login/* /var/www/html/notes/login/
ADD --chown=www-data:www-data notes/*.php /var/www/html/notes/
ADD --chown=www-data:www-data notes/*.json /var/www/html/notes/
ADD --chown=www-data:www-data index.php /var/www/html/index.php
ADD php.ini-mail /usr/local/etc/php/php.ini
RUN chmod 660 /var/www/html/notes/app/*
RUN chmod 660 /var/www/html/notes/login/*
RUN chmod 660 /var/www/html/notes/*

# Добавим свой php.ini, можем в нем определять свои значения конфига
#ADD php.ini /usr/local/etc/php/conf.d/40-custom.ini

# Сообщаем, какие порты контейнера слушать
#EXPOSE 80 3306
EXPOSE 80

# Указываем рабочую директорию для PHP
WORKDIR /var/www

#RUN apachectl -D FOREGROUND

# Запускаем контейнер
# Из документации: The main purpose of a CMD is to provide defaults for an executing container. These defaults can include an executable, 
# or they can omit the executable, in which case you must specify an ENTRYPOINT instruction as well.
#CMD ["bash"]
#CMD ["php-fpm"]
#CMD ["service mysql start"]
#CMD ["mysqld"]
