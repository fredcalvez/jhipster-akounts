import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bridge-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bridgeUserEntity = useAppSelector(state => state.bridgeUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bridgeUserDetailsHeading">
          <Translate contentKey="akountsApp.bridgeUser.detail.title">BridgeUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bridgeUserEntity.id}</dd>
          <dt>
            <span id="uuid">
              <Translate contentKey="akountsApp.bridgeUser.uuid">Uuid</Translate>
            </span>
          </dt>
          <dd>{bridgeUserEntity.uuid}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="akountsApp.bridgeUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{bridgeUserEntity.email}</dd>
          <dt>
            <span id="pass">
              <Translate contentKey="akountsApp.bridgeUser.pass">Pass</Translate>
            </span>
          </dt>
          <dd>{bridgeUserEntity.pass}</dd>
          <dt>
            <span id="lastLoginDate">
              <Translate contentKey="akountsApp.bridgeUser.lastLoginDate">Last Login Date</Translate>
            </span>
          </dt>
          <dd>
            {bridgeUserEntity.lastLoginDate ? (
              <TextFormat value={bridgeUserEntity.lastLoginDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bridge-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bridge-user/${bridgeUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BridgeUserDetail;
