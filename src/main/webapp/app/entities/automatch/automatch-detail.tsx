import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './automatch.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AutomatchDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const automatchEntity = useAppSelector(state => state.automatch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="automatchDetailsHeading">
          <Translate contentKey="akountsApp.automatch.detail.title">Automatch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{automatchEntity.id}</dd>
          <dt>
            <span id="matchstring">
              <Translate contentKey="akountsApp.automatch.matchstring">Matchstring</Translate>
            </span>
          </dt>
          <dd>{automatchEntity.matchstring}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="akountsApp.automatch.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>
            {automatchEntity.updateTime ? <TextFormat value={automatchEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUsedTime">
              <Translate contentKey="akountsApp.automatch.lastUsedTime">Last Used Time</Translate>
            </span>
          </dt>
          <dd>
            {automatchEntity.lastUsedTime ? <TextFormat value={automatchEntity.lastUsedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="useCount">
              <Translate contentKey="akountsApp.automatch.useCount">Use Count</Translate>
            </span>
          </dt>
          <dd>{automatchEntity.useCount}</dd>
          <dt>
            <span id="defaultTag">
              <Translate contentKey="akountsApp.automatch.defaultTag">Default Tag</Translate>
            </span>
          </dt>
          <dd>{automatchEntity.defaultTag}</dd>
        </dl>
        <Button tag={Link} to="/automatch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/automatch/${automatchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AutomatchDetail;
