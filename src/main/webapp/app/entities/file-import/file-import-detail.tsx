import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './file-import.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FileImportDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fileImportEntity = useAppSelector(state => state.fileImport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileImportDetailsHeading">
          <Translate contentKey="akountsApp.fileImport.detail.title">FileImport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fileImportEntity.id}</dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="akountsApp.fileImport.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{fileImportEntity.fileName}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="akountsApp.fileImport.status">Status</Translate>
            </span>
          </dt>
          <dd>{fileImportEntity.status}</dd>
          <dt>
            <span id="reviewDate">
              <Translate contentKey="akountsApp.fileImport.reviewDate">Review Date</Translate>
            </span>
          </dt>
          <dd>
            {fileImportEntity.reviewDate ? <TextFormat value={fileImportEntity.reviewDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="filePath">
              <Translate contentKey="akountsApp.fileImport.filePath">File Path</Translate>
            </span>
          </dt>
          <dd>{fileImportEntity.filePath}</dd>
        </dl>
        <Button tag={Link} to="/file-import" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file-import/${fileImportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FileImportDetail;
